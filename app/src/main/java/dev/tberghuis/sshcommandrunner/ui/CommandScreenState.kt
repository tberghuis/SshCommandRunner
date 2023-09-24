package dev.tberghuis.sshcommandrunner.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.tberghuis.sshcommandrunner.tmp2.Command

class CommandScreenState {
  var title by mutableStateOf("")
  var host by mutableStateOf("")
  var user by mutableStateOf("")
  var password by mutableStateOf("")
  var command by mutableStateOf("")

  var passwordHidden by mutableStateOf(true)

  var isLocalPortForward by mutableStateOf(false)
  var localHost by mutableStateOf("")
  var localPort by mutableStateOf("")
  var remoteHost by mutableStateOf("")
  var remotePort by mutableStateOf("")


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

      commandScreenState.isLocalPortForward = c.isLocalPortForward
      commandScreenState.localHost = c.localHost
      commandScreenState.localPort = c.localPort
      commandScreenState.remoteHost = c.remoteHost
      commandScreenState.remotePort = c.remotePort

      return commandScreenState
    }
  }
}