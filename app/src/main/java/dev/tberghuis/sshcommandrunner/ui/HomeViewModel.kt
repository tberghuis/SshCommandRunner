package dev.tberghuis.sshcommandrunner.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.tberghuis.sshcommandrunner.MyApplication
import dev.tberghuis.sshcommandrunner.tmp2.Command
import kotlinx.coroutines.flow.Flow

class HomeViewModel(application: Application) : AndroidViewModel(application) {
  private val commandDao = (application as MyApplication).database.commandDao()

//  var commandList: List<Command> by mutableStateOf(listOf())

//  init {
//    viewModelScope.launch {
//
//    }
//  }

  fun getCommandList(): Flow<List<Command>> {
    return commandDao.getAll()
  }
}