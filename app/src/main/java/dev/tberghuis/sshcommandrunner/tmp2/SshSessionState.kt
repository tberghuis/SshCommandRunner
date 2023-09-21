package dev.tberghuis.sshcommandrunner.tmp2

import kotlinx.coroutines.flow.MutableStateFlow

class SshSessionState {
  val commandOutput = MutableStateFlow(listOf<String>())
  val error = MutableStateFlow<String?>(null)
}