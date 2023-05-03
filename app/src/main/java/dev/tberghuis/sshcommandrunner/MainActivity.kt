package dev.tberghuis.sshcommandrunner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.tberghuis.sshcommandrunner.ui.AddCommand
import dev.tberghuis.sshcommandrunner.ui.EditCommand
import dev.tberghuis.sshcommandrunner.ui.Home
import dev.tberghuis.sshcommandrunner.ui.RunCommand
import dev.tberghuis.sshcommandrunner.ui.theme.SshCommandRunnerTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SshCommandRunnerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          MyAppNavHost()
        }
      }
    }
  }
}


@Composable
fun MyAppNavHost(
) {

  val navController = rememberNavController()


  NavHost(
    navController = navController, startDestination = "home"
  ) {
    composable("home") {
      Home(
        navigateAddCommand = { navController.navigate("add_command") },
        navigateRunCommand = { id -> navController.navigate("command/$id") },
        navigateEditCommand = { id -> navController.navigate("edit_command/$id") }
      )
    }
    composable("add_command") {
      AddCommand(
        popHome = {
          navController.popBackStack()
        }
      )
    }
    composable(
      "edit_command/{id}",
      arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) {
      EditCommand(
        popHome = {
          navController.popBackStack()
        }
      )
    }



    composable(
      "command/{id}",
      arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) {
      RunCommand()
    }

  }
}