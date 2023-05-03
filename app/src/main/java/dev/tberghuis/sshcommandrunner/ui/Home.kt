package dev.tberghuis.sshcommandrunner.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tberghuis.sshcommandrunner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
  navigateAddCommand: () -> Unit,
  navigateRunCommand: (Int) -> Unit,
  navigateEditCommand: (Int) -> Unit,
  vm: HomeViewModel = viewModel()
) {

  val commandList by vm.getCommandList().collectAsStateWithLifecycle(initialValue = listOf())

  Scaffold(
    topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) },

    floatingActionButton = {
      FloatingActionButton(
        onClick = navigateAddCommand,
      ) {
        Icon(
          Icons.Rounded.Add,
          contentDescription = "add command"
        )
      }
    },

    ) { padding ->
    LazyColumn(
      modifier = Modifier.padding(padding),
      contentPadding = PaddingValues(10.dp),
    ) {

      items(commandList) { command ->


        Card(modifier = Modifier
          .padding(bottom = 10.dp)
          .clickable {
            navigateRunCommand(command.id)
          }
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
              command.title,
              modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1f),
            )
            IconButton(onClick = {
              navigateEditCommand(command.id)
            }) {
              Icon(imageVector = Icons.Default.Edit, contentDescription = "edit")
            }
          }
        }
      }

      // clear the FloatingActionButton
      item {
        Spacer(Modifier.height(60.dp))
      }
    }

  }
}