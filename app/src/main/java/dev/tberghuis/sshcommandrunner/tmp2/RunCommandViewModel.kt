package dev.tberghuis.sshcommandrunner.tmp2

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.tmp.SshService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RunCommandViewModel(
  application: Application,
  savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

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

  init {
    application.startForegroundService(sshServiceIntent)
    application.bindService(sshServiceIntent, connection, 0)
    val id: Int = checkNotNull(savedStateHandle["id"])
    viewModelScope.launch {
      val sshService = sshServiceStateFlow.filterNotNull().first()
      sshService.runCommand(id)
    }
  }


  fun hangup() {

  }
}