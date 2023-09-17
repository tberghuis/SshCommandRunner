package dev.tberghuis.sshcommandrunner.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TmpScreen(
  vm: TmpScreenVm = viewModel()
) {
  Column {
    Text("hello tmp screen")
    Button(onClick = {
      vm.startSshService()
    }) {
      Text("start ssh service")
    }

    Button(onClick = { vm.bindSshService() }) {
      Text("bind ssh service")
    }

    Button(onClick = { vm.willitblend() }) {
      Text("willitblend")
    }

  }
}