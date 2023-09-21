package dev.tberghuis.sshcommandrunner.tmp2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun RunCommandContent(
  padding: PaddingValues, vm: RunCommandViewModel
) {

//  val sshController by vm.sshControllerFlow.collectAsState()
//  if (sshController == null) {
//    return
//  }

  val commandOutput by vm.vmSshSessionState.commandOutput.collectAsState()
  val error by vm.vmSshSessionState.error.collectAsState()

  Column(Modifier.padding(padding)) {
    Text("output: ${commandOutput}")
    Text("error: ${error}")
  }
}