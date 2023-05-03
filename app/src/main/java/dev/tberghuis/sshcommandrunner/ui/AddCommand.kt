package dev.tberghuis.sshcommandrunner.ui

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
fun AddCommand(
  popHome: () -> Unit,
  vm: AddCommandViewModel = viewModel()
) {
  Scaffold(
    topBar = { TopAppBar(title = { Text("Add Command") }) },
  ) { padding ->

    val onDone = {
      if (vm.validateFields()) {
        vm.insertCommand()
        popHome()
      }
    }

    CommandContent(padding, vm.commandScreenState, onDone) {
      Button(
        modifier = Modifier.padding(top = 20.dp),
        onClick = { onDone() }) {
        Text("ADD")
      }
    }
  }
}