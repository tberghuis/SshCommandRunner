package dev.tberghuis.sshcommandrunner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCommand(
  popHome: () -> Unit, vm: EditCommandViewModel = viewModel()
) {
  Scaffold(
    topBar = { TopAppBar(title = { Text("Edit Command") }) },
  ) { padding ->
    if (vm.initialised) {
      val onDone = {
        vm.save()
        popHome()
      }
      CommandContent(padding, vm.commandScreenState, onDone) {
        Row(
          modifier = Modifier.padding(top = 20.dp),
          horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
          Button(onClick = { onDone() }) {
            Text("SAVE")
          }
          Button(onClick = {
            vm.delete()
            popHome()
          }) {
            Text("DELETE")
          }
        }
      }

    }
  }
}