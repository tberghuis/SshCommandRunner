package dev.tberghuis.sshcommandrunner.tmp2

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.tmp.SshController
import dev.tberghuis.sshcommandrunner.tmp.SshService
import kotlinx.coroutines.flow.MutableStateFlow

class RunCommandViewModel(
  application: Application,
  savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

//  val sshControllerFlow = MutableStateFlow<SshController?>(null)

  val vmSshSessionState = SshSessionState()



  // do I even need hold this, should hangup through SshController
  @SuppressLint("StaticFieldLeak")
  var sshService: SshService? = null

  private val sshServiceIntent = Intent(application, SshService::class.java)

  private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      val binder = service as SshService.LocalBinder
      sshService = binder.getService()
      sshService?.runCommand(checkNotNull(savedStateHandle["id"]), vmSshSessionState)
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

  }
}