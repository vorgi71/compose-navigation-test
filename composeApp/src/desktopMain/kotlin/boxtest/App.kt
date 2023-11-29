package boxtest

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun App() {
  MaterialTheme {
    Box(
      modifier = Modifier
        .background(Color.DarkGray)
        .fillMaxSize()
        .padding(12.dp)
    ) {
      BoxWithConstraints(
        modifier = Modifier
          .background(Color.Gray)
          .fillMaxSize()
      ) {
        val boxWidth=this.constraints.maxWidth
        val boxHeight=this.constraints.maxHeight
        val size=Math.min(boxWidth/30.0,boxHeight/32.0).toInt()
        for(x in 0..<30) {
          for (y in 0..<32) {
            Plant(x,y,size)
          }
        }
      }
    }
  }
}

@Composable
fun Plant(x:Int,y:Int,size:Int) {
  Box(modifier = Modifier
    .layout { measurable, constraints ->
      val placeable = measurable.measure(constraints)
      layout(size,size) {
        placeable.placeRelative(x*size, y*size)
      }
    }
    .border(1.dp,Color.DarkGray)
    .height(size.dp)
    .width(size.dp)
    .background(Color.Green))
}