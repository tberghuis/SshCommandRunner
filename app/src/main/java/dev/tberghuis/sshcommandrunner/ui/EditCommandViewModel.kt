package dev.tberghuis.sshcommandrunner.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.MyApplication
import dev.tberghuis.sshcommandrunner.tmp2.Command
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class EditCommandViewModel(
  application: Application,
  savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {
  private val commandDao = (application as MyApplication).database.commandDao()

  var initialised by mutableStateOf(false)

  private val id: Int = checkNotNull(savedStateHandle["id"])

  // composition (share code add and edit screens)
  lateinit var commandScreenState: CommandScreenState

  init {
    viewModelScope.launch(Main) {
      val c = commandDao.loadCommandById(id)
      commandScreenState = CommandScreenState.create(c)
      initialised = true
    }
  }

  fun save() {
    viewModelScope.launch(IO) {
      commandDao.update(Command.create(id, commandScreenState))
    }
  }

  fun delete() {
    viewModelScope.launch(IO) {
      commandDao.deleteById(id)
    }
  }

}