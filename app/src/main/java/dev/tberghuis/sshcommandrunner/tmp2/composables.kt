package dev.tberghuis.sshcommandrunner.tmp2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun RunCommandContent(
  padding: PaddingValues, vm: RunCommandViewModel
) {
//  Column(Modifier.padding(padding)) {
//    Text("output: ${vm.commandOutput}")
//    Text("error: ${vm.error}")
//  }

  if (vm.command == null) {
    return
  }
  val command = vm.command!!

  val lazyListState = rememberLazyListState()

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