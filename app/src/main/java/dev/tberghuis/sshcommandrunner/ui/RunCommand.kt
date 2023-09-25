package dev.tberghuis.sshcommandrunner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunCommand(
  vm: RunCommandViewModel = viewModel()
) {
  // doitwrong
  val topBarText = if (vm.command == null) {
    ""
  } else vm.command!!.title

  Scaffold(
    topBar = { TopAppBar(title = { Text(topBarText) }) },
    bottomBar = {
      BottomAppBar {
        // wrong way of centering button
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
          Button(onClick = { vm.hangup() }) {
            Text("Hangup")
          }
        }
      }
    },
  ) { padding ->
    RunCommandContent(padding, vm)
  }
}

@Composable
fun RunCommandContent(
  padding: PaddingValues, vm: RunCommandViewModel
) {
  if (vm.command == null) {
    return
  }
  val command = vm.command!!
  val lazyListState = rememberLazyListState()

  LaunchedEffect(vm.commandOutput) {
    if (vm.commandOutput.lastIndex >= 0) {
      lazyListState.animateScrollToItem(vm.commandOutput.lastIndex)
    }
  }
  LazyColumn(
    modifier = Modifier
      .padding(padding)
      .fillMaxSize()
      .background(Color.Black), lazyListState
  ) {
    item {
      Text(buildAnnotatedString {
        append(
          AnnotatedString("${command.user}@${command.host} ~ $", spanStyle = SpanStyle(Color.Green))
        )
        append(
          AnnotatedString(" ${command.command}", spanStyle = SpanStyle(Color.White))
        )
      })
    }

    if (vm.error == null) {
      items(vm.commandOutput) { line ->
        Text(line, color = Color.White)
      }
    } else {
      item {
        Text(vm.error!!, color = Color.Red)
      }
    }
  }
}