import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModel


@Composable
fun App() {
  PreComposeApp {
    val navigator = rememberNavigator()
    MaterialTheme {
      NavHost(
        navigator = navigator,
        initialRoute = "/home"
      ) {
        scene(route = "/home") {
          val homeViewModel = viewModel {
            HomeViewModel()
          }
          val name by homeViewModel.name.collectAsStateWithLifecycle()
          Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              text = "Greet Me!",
              style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
              value = name,
              maxLines = 1,
              label = { Text(text = "Enter your name") },
              onValueChange = homeViewModel::setName
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
              onClick = {
                navigator.navigate(route = "/greeting/$name")
              }
            ) {
              Text(text = "GO!")
            }
          }
        }
        scene(route = "/greeting/{name}") { backStackEntry ->
          backStackEntry.path<String>("name")?.let { name ->
            Column(
              modifier = Modifier.fillMaxSize(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Text(
                text = name,
                style = MaterialTheme.typography.h6
              )
              Spacer(modifier = Modifier.height(30.dp))
              Button(onClick = navigator::goBack) {
                Text(text = "GO BACK!")
              }
            }
          }
        }
      }
    }
  }
}

class HomeViewModel : ViewModel() {
  val name = MutableStateFlow("")
  fun setName(value: String) {
    name.update { value }
  }
}

