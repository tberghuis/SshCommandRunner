package dev.tberghuis.sshcommandrunner.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tberghuis.sshcommandrunner.BuildConfig

@Composable
fun TmpScreen(
  vm: TmpScreenVm = viewModel()
) {
  Column {
    Text("hello tmp screen")


    Button(onClick = { vm.runCommand() }) {
      Text("run command")
    }

    Button(onClick = { vm.hangup() }) {
      Text("hangup")
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
    Text("ssh service NOT running")
    return
  }
//  val count = sshService!!.sshServiceState.count

  Text("ssh service running")


  Text("output: ${sshService!!.commandOutput}")
  Text("error: ${sshService!!.error}")

}


