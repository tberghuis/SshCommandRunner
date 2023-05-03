package dev.tberghuis.sshcommandrunner.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.MyApplication
import dev.tberghuis.sshcommandrunner.data.Command
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


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