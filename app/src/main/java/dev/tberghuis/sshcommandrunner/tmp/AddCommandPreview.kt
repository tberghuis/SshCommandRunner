package dev.tberghuis.sshcommandrunner.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.tberghuis.sshcommandrunner.ui.theme.SshCommandRunnerTheme

// TODO finish design, doitwrong mvp
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddCommandPreview() {

  SshCommandRunnerTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

      Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        TextField(
          value = "my title",
          onValueChange = { },
          label = { Text("Title") },
        )

        TextField(
          value = "10.0.2.2",
          onValueChange = { },
          label = { Text("Host") },
        )

        TextField(
          value = "tom",
          onValueChange = { },
          label = { Text("User") },
        )

        TextField(
          value = "password",
          onValueChange = { },
          label = { Text("Password") },
        )

        TextField(
          value = "ping google.com",
          onValueChange = { },
          label = { Text("Command") },
        )

        Button(onClick = {}) {
          Text("Add")
        }

      }

    }
  }
}
