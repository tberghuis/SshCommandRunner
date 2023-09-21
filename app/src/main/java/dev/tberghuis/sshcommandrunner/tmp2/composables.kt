package dev.tberghuis.sshcommandrunner.tmp2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RunCommandContent(
  padding: PaddingValues, vm: RunCommandViewModel
) {
  Column(Modifier.padding(padding)) {
    Text("output: ${vm.commandOutput}")
    Text("error: ${vm.error}")
  }
}