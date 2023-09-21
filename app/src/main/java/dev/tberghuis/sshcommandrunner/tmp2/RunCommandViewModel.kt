package dev.tberghuis.sshcommandrunner.tmp2

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.data.Command
import dev.tberghuis.sshcommandrunner.tmp.SshService
import dev.tberghuis.sshcommandrunner.util.logd
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RunCommandViewModel(
  private val application: Application,
  savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {
  var command: Command? by mutableStateOf(null)
  var commandOutput by mutableStateOf(listOf<String>())
  var error: String? by mutableStateOf(null)

  // do I even need hold this, should hangup through SshController
  @SuppressLint("StaticFieldLeak")
  var sshService: SshService? = null

  private val sshServiceIntent = Intent(application, SshService::class.java)

  private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      val binder = service as SshService.LocalBinder
      sshService = binder.getService()
      sshService?.runCommand(checkNotNull(savedStateHandle["id"])) { command, sshSessionState ->
        sshSessionState.commandOutput.onEach { commandOutput = it }.launchIn(viewModelScope)
        sshSessionState.error.onEach { error = it }.launchIn(viewModelScope)
        this@RunCommandViewModel.command = command
      }
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      sshService = null
    }
  }

  init {
    application.startForegroundService(sshServiceIntent)
    application.bindService(sshServiceIntent, connection, 0)
  }


  fun hangup() {
    sshService?.hangup()
    application.unbindService(connection)
    application.stopService(sshServiceIntent)
  }
}