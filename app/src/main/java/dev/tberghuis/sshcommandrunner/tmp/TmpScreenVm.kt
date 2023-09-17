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

  private val sshServiceStateFlow = MutableStateFlow<SshService?>(null)


  private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      val binder = service as SshService.LocalBinder
      sshServiceStateFlow.value = binder.getService()
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      sshServiceStateFlow.value = null
    }
  }

  fun allSteps() {
    logd("TmpScreenVm allSteps")
    val intent = Intent(application, SshService::class.java)
    application.startForegroundService(intent)
    application.bindService(intent, connection, 0)

    viewModelScope.launch {
      val sshService = sshServiceStateFlow.filterNotNull().first()
      sshService.willitblend()
    }
  }


}