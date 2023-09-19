package dev.tberghuis.sshcommandrunner.tmp

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.util.logd
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TmpScreenVm(private val application: Application) : AndroidViewModel(application) {

  val sshServiceStateFlow = MutableStateFlow<SshService?>(null)
  private val sshServiceIntent = Intent(application, SshService::class.java)


  private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      val binder = service as SshService.LocalBinder
      sshServiceStateFlow.value = binder.getService()
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      sshServiceStateFlow.value = null
    }
  }

  fun runPortForward() {
    logd("TmpScreenVm allSteps")
    application.startForegroundService(sshServiceIntent)
    application.bindService(sshServiceIntent, connection, 0)

    viewModelScope.launch {
      val sshService = sshServiceStateFlow.filterNotNull().first()
      sshService.runCommand(1)
    }
  }

  override fun onCleared() {
    // todo this when hangup
    //  application.unbindService(connection)
    //  application.stopService(sshServiceIntent)
    super.onCleared()
  }
}