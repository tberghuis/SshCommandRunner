package dev.tberghuis.sshcommandrunner.tmp2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.tberghuis.sshcommandrunner.util.logd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SshSessionState {
  val commandOutput = MutableStateFlow(listOf<String>())
  val error = MutableStateFlow<String?>(null)

  // doitwrong
//  fun collectFrom(scope: CoroutineScope, state: SshSessionState) {
//    logd("collectFrom")
//    scope.launch {
//      state.error.collect {
//        logd("error collect")
//        error.value = it
//      }
//    }
//
//    scope.launch {
//      state.commandOutput.collect {
//        logd("commandOutput collect")
//        commandOutput.value = it
//      }
//    }
//  }
}