package dev.tberghuis.sshcommandrunner.tmp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.tberghuis.sshcommandrunner.util.logd

class TmpScreenVm(private val application: Application) : AndroidViewModel(application) {


  fun startForeground(){
    logd("TmpScreenVm startForeground")
  }
}