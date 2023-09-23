package dev.tberghuis.sshcommandrunner.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CommandContent(
  padding: PaddingValues,
  commandScreenState: CommandScreenState,
  onDone: () -> Unit,
  action: @Composable () -> Unit
) {
  val configuration = LocalConfiguration.current
  val screenWidth = configuration.screenWidthDp

  val columnWidth = Modifier.then(
    if (screenWidth > 500) Modifier.width(450.dp)
    else Modifier.fillMaxWidth()
  )
  val localFocusManager = LocalFocusManager.current

  Column(
    modifier = Modifier
      .padding(padding)
      .padding(10.dp)
      .verticalScroll(rememberScrollState())
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    OutlinedTextField(
      modifier = Modifier.then(columnWidth),
      value = commandScreenState.title,
      onValueChange = {
        commandScreenState.title = it
      },
      label = { Text("Title") },
      supportingText = {
        if (commandScreenState.errorTitle != null) Text(commandScreenState.errorTitle!!)
      },
      isError = commandScreenState.errorTitle != null,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
      ),
    )
    OutlinedTextField(
      modifier = Modifier.then(columnWidth),
      value = commandScreenState.host,
      onValueChange = {
        commandScreenState.host = it
      },
      label = { Text("Host") },
      supportingText = {
        if (commandScreenState.errorHost != null) Text(commandScreenState.errorHost!!)
      },
      isError = commandScreenState.errorHost != null,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
      ),
    )
    OutlinedTextField(
      modifier = Modifier.then(columnWidth),
      value = commandScreenState.user,
      onValueChange = {
        commandScreenState.user = it
      },
      label = { Text("User") },
      supportingText = {
        if (commandScreenState.errorUser != null) Text(commandScreenState.errorUser!!)
      },
      isError = commandScreenState.errorUser != null,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
      ),
    )

    OutlinedTextField(
      modifier = Modifier.then(columnWidth),
      value = commandScreenState.password,
      onValueChange = {
        commandScreenState.password = it
      },
      label = { Text("Password") },
      supportingText = {
        if (commandScreenState.errorPassword != null) Text(commandScreenState.errorPassword!!)
      },
      isError = commandScreenState.errorPassword != null,
      visualTransformation = if (commandScreenState.passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
      ),
      keyboardActions = KeyboardActions(onNext = {
        localFocusManager.moveFocus(FocusDirection.Down)
      }),
      trailingIcon = {
        IconButton(onClick = {
          commandScreenState.passwordHidden = !commandScreenState.passwordHidden
        }) {
          val visibilityIcon =
            if (commandScreenState.passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
          // Please provide localized description for accessibility services
          val description =
            if (commandScreenState.passwordHidden) "Show password" else "Hide password"
          Icon(imageVector = visibilityIcon, contentDescription = description)
        }
      })

    OutlinedTextField(
      modifier = Modifier.then(columnWidth),
      value = commandScreenState.command,
      onValueChange = {
        commandScreenState.command = it
      },
      label = { Text("Command") },
      supportingText = {
        if (commandScreenState.errorCommand != null) Text(commandScreenState.errorCommand!!)
      },
      isError = commandScreenState.errorCommand != null,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done
      ),
      keyboardActions = KeyboardActions(onNext = {
        localFocusManager.moveFocus(FocusDirection.Down)
      }),
    )

    Row {
      Checkbox(
        checked = commandScreenState.isLocalPortForward,
        onCheckedChange = {
          commandScreenState.isLocalPortForward = it
        },
      )
      Text("Local port forward")
    }

    if (commandScreenState.isLocalPortForward) {
      Row {
        OutlinedTextField(
          value = commandScreenState.localHost,
          onValueChange = {
            commandScreenState.localHost = it
          },
          label = { Text("local host") },
        )
        OutlinedTextField(
          value = commandScreenState.localPort,
          onValueChange = {
            commandScreenState.localPort = it
          },
          label = { Text("local port") },
        )
      }
      Row {
        OutlinedTextField(
          value = commandScreenState.remoteHost,
          onValueChange = {
            commandScreenState.remoteHost = it
          },
          label = { Text("remote host") },
        )
        OutlinedTextField(
          value = commandScreenState.remotePort,
          onValueChange = {
            commandScreenState.remotePort = it
          },
          label = { Text("remote port") },
        )

      }
    }




    action()

  }
}