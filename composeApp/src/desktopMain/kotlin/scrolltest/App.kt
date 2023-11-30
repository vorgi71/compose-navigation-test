package scrolltest

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Preview
@Composable
fun App() {
  var outputs by remember {
    mutableStateOf(MutableList<String>(10) {
      "item: $it"
    })
  }
  var text by remember { mutableStateOf("") }

  MaterialTheme {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xa4e010))
    ) {
      LazyColumn {
        items(outputs.size) { index ->
          Row {
            Text("✅")
            Text(outputs[index])
          }
        }
      }
      Row {
        TextField(modifier = Modifier
          .fillMaxSize()
          .align(Alignment.Top),
          value = text,
          onValueChange = {
            text = it
          }
        )
      }
    }
  }
}