import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import moe.tlaster.precompose.PreComposeApp

fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    App()
  }
}

@Preview
@Composable
fun AppDesktopPreview() {
  App()
}