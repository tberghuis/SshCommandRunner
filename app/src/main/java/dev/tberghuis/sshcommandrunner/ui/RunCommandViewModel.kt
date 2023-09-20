package dev.tberghuis.sshcommandrunner.ui

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.MyApplication
import dev.tberghuis.sshcommandrunner.data.Command
import dev.tberghuis.sshcommandrunner.util.logd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.connection.channel.direct.Session
import net.schmizz.sshj.connection.channel.direct.Signal
import net.schmizz.sshj.transport.verification.PromiscuousVerifier

class XRunCommandViewModel(
  application: Application,
  savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {
  private val commandDao = (application as MyApplication).database.commandDao()
  var command: Command? by mutableStateOf(null)

  var commandOutput by mutableStateOf(listOf<String>())

  var error: String? by mutableStateOf(null)

  private val ssh = SSHClient()
  private var session: Session? = null
  private var cmd: Session.Command? = null
  private var cmdJob: Job? = null

  init {
    val id: Int = checkNotNull(savedStateHandle["id"])
    logd("RunCommandViewModel init id $id")
    viewModelScope.launch() {
      command = commandDao.loadCommandById(id)
      withContext(IO) {
        runCommand()
      }
    }
  }

  private fun runCommand() {
    val c = command!!
    ssh.addHostKeyVerifier(PromiscuousVerifier())
    viewModelScope.launch(Dispatchers.IO) {
      try {
        ssh.connect(c.host)
        ssh.authPassword(c.user, c.password)
        session = ssh.startSession()
        cmd = session!!.exec(c.command)
        val sshFlow = cmd!!.inputStream.bufferedReader().lineSequence().asFlow()
        cmdJob = launch() {
          sshFlow.cancellable().collect {
            Log.d("xxx", "line: $it")
            commandOutput += it
          }
        }
      } catch (e: Exception) {
        logd("error $e")
        error = e.toString()
      }
    }
  }

  // i don't know what i am doing
  fun hangup() {
    viewModelScope.launch(IO) {
      try {
        cmd?.signal(Signal.HUP)
        cmdJob?.cancel()
        cmd?.join()
        session?.close()
        ssh.disconnect()
      } catch (e: Exception) {
        logd("error: $e")
      }
    }
  }

  override fun onCleared() {
    logd("RunCommandViewModel onCleared")
    hangup()
    super.onCleared()
  }
}