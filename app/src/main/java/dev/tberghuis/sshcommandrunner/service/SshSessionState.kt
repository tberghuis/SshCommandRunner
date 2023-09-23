package dev.tberghuis.sshcommandrunner.service

import kotlinx.coroutines.flow.MutableStateFlow

class SshSessionState {
  val commandOutput = MutableStateFlow(listOf<String>())
  val error = MutableStateFlow<String?>(null)
}