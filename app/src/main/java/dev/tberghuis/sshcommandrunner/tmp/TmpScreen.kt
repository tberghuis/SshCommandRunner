package dev.tberghuis.sshcommandrunner.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TmpScreen(
  vm: TmpScreenVm = viewModel()
) {
  Column {
    Text("hello tmp screen")


    Button(onClick = { vm.allSteps() }) {
      Text("all steps")
    }

    ViewSshServiceState()

  }
}

@Composable
fun ViewSshServiceState(
  vm: TmpScreenVm = viewModel()
) {
  val sshService by vm.sshServiceStateFlow.collectAsState()
  if (sshService == null) {
    return
  }
  val count = sshService!!.sshServiceState.count

  Text("count: $count")
}