package dev.tberghuis.sshcommandrunner.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.MyApplication
import dev.tberghuis.sshcommandrunner.tmp2.Command
import dev.tberghuis.sshcommandrunner.util.logd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCommandViewModel(application: Application) : AndroidViewModel(application) {
  private val commandDao = (application as MyApplication).database.commandDao()

  val commandScreenState = CommandScreenState()

  init {
    logd("AddCommandViewModel init commandDao $commandDao")
  }

  // todo validate form function
  // Add button enabled

  fun validateFields(): Boolean {
    var retBool = true
    if (commandScreenState.title.isBlank()) {
      commandScreenState.errorTitle = "Can not be blank"
      retBool = false
    } else {
      commandScreenState.errorTitle = null
    }
    if (commandScreenState.host.isBlank()) {
      commandScreenState.errorHost = "Can not be blank"
      retBool = false
    } else {
      commandScreenState.errorHost = null
    }
    if (commandScreenState.user.isBlank()) {
      commandScreenState.errorUser = "Can not be blank"
      retBool = false
    } else {
      commandScreenState.errorUser = null
    }
    if (commandScreenState.password.isBlank()) {
      commandScreenState.errorPassword = "Can not be blank"
      retBool = false
    } else {
      commandScreenState.errorPassword = null
    }
//    if (commandScreenState.command.isBlank()) {
//      commandScreenState.errorCommand = "Can not be blank"
//      retBool = false
//    }

    return retBool
  }

  fun insertCommand() {
    viewModelScope.launch(Dispatchers.IO) {
      commandDao.insertAll(Command.create(css = commandScreenState))
    }
  }

}