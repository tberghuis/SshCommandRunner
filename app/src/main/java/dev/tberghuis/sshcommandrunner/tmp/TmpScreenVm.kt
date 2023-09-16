package dev.tberghuis.sshcommandrunner.tmp

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import dev.tberghuis.sshcommandrunner.util.logd

class TmpScreenVm(private val application: Application) : AndroidViewModel(application) {


  fun startSshService() {
    logd("TmpScreenVm startForeground")
    val intent = Intent(application, SshService::class.java)
    application.startForegroundService(intent)
  }
}