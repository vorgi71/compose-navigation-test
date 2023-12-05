package scrolltest

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    MaterialTheme {
      Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Box(Modifier.fillMaxSize()) {
          val textList = remember { mutableStateListOf<String>() }
          val textFieldValue = remember { mutableStateOf("") }

          val scrollState:LazyListState = rememberLazyListState(0)

          Box(modifier = Modifier.padding(bottom = 48.dp)) {
            LazyColumn(state = scrollState,
                       contentPadding = PaddingValues(end = 16.dp),
                       modifier = Modifier.fillMaxWidth()) {
              items(textList.size) { index ->
                Text(textList[index])
              }
            }

            VerticalScrollbar(
              modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
              adapter = rememberScrollbarAdapter(scrollState),
              style = defaultScrollbarStyle()
            )
          }

          Spacer(modifier = Modifier)

          Column(
            Modifier
              .align(Alignment.BottomStart)
              .fillMaxWidth()
              .padding(8.dp)
              .background(Color.LightGray)
          ) {
            TextField(
              value = textFieldValue.value,
              onValueChange = { textFieldValue.value = it },
              modifier = Modifier.onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Enter) {
                  textList.add(textFieldValue.value)
                  textFieldValue.value = ""
                  true
                } else {
                  false
                }
              }
            )
          }
        }
      }
    }
  }
}