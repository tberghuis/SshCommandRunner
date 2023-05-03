package dev.tberghuis.sshcommandrunner.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.tberghuis.sshcommandrunner.data.Command

class CommandScreenState {
  var title by mutableStateOf("")
  var host by mutableStateOf("")
  var user by mutableStateOf("")
  var password by mutableStateOf("")
  var command by mutableStateOf("")

  var passwordHidden by mutableStateOf(true)

  // doitwrong i.e. first naive sln attempt
  var errorTitle: String? by mutableStateOf(null)
  var errorHost: String? by mutableStateOf(null)
  var errorUser: String? by mutableStateOf(null)
  var errorPassword: String? by mutableStateOf(null)
  var errorCommand: String? by mutableStateOf(null)

  companion object {
    fun create(c: Command): CommandScreenState {
      val commandScreenState = CommandScreenState()
      commandScreenState.title = c.title
      commandScreenState.host = c.host
      commandScreenState.user = c.user
      commandScreenState.password = c.password
      commandScreenState.command = c.command
      return commandScreenState
    }
  }
}