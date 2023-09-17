package dev.tberghuis.sshcommandrunner.tmp

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import dev.tberghuis.sshcommandrunner.util.logd

class TmpScreenVm(private val application: Application) : AndroidViewModel(application) {


  private var sshService: SshService? = null


  private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      val binder = service as SshService.LocalBinder
      sshService = binder.getService()
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      sshService = null
    }
  }


  fun startSshService() {
    logd("TmpScreenVm startSshService")
    val intent = Intent(application, SshService::class.java)
    application.startForegroundService(intent)
  }


  fun bindSshService() {
    logd("TmpScreenVm bindSshService")
    val intent = Intent(application, SshService::class.java)
    application.bindService(intent, connection, 0)
  }

  fun willitblend() {
    logd("TmpScreenVm willitblend")
    sshService?.willitblend()
  }


}