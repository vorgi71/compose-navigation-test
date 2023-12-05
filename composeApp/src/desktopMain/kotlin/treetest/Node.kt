package treetest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

interface TreeNode {
  fun getName(): String
  fun hasChildren(): Boolean

  fun getChildren(): List<TreeNode>

  fun getFlatList(rootNode: TreeNode = this, level: Int = 0, isExpanded: Boolean = true): List<TreeListElement> {
    val returnValue = mutableListOf<TreeListElement>()
    returnValue.add(TreeListElement(rootNode.getName(), rootNode.hasChildren(), isExpanded = isExpanded, level = level))
    if (rootNode.hasChildren()) {
      for (childNode in rootNode.getChildren()) {
        returnValue.addAll(getFlatList(childNode, level + 1, false))
      }
    }
    return returnValue
  }
}

data class TreeListElement(
  val name: String,
  val hasChildren: Boolean,
  val level: Int = 0,
  var isExpanded: Boolean = false
) {
  fun isVisibleInList(treeList: List<TreeListElement>): Boolean {
    var index = treeList.indexOf(this)
    if (index < 0) {
      return false
    }
    if (index == 0) return true
    val levelAtThis = level
    val list:MutableList<TreeListElement> = mutableListOf()
    while (index-- > 0) {
      val topNode = treeList[index]
      if (topNode.level < levelAtThis) {
        list.add(topNode)
      }
    }
    return list.all { it.isExpanded }

  }
}

class Node(private var name: String, private var children: MutableList<Node> = mutableListOf()) : TreeNode {
  override fun getName(): String {
    return name
  }

  override fun hasChildren(): Boolean {
    return children.size > 0
  }

  override fun getChildren(): List<TreeNode> {
    return children.toList()
  }

}

@Composable
fun Tree(rootNode: TreeNode) {
  val flatList = remember { mutableStateListOf(*rootNode.getFlatList().toTypedArray()) }

  val updateTreeState: (Int, Boolean) -> Unit = { index, expand ->
    if (index >= 0) {
      flatList[index] = flatList[index].copy(isExpanded = expand)
    }
  }

  LazyColumn {
    items(flatList.size) {
      TreeItem(flatList, flatList[it], updateTreeState)
    }
  }
}

@Composable
fun TreeItem(
  flatList: List<TreeListElement>,
  treeListElement: TreeListElement,
  updateTreeState: (Int, Boolean) -> Unit
) {
  AnimatedVisibility(
    visible = treeListElement.isVisibleInList(flatList),
    enter = expandVertically(),
    exit = shrinkVertically()
  ) {
    Row(
      modifier = Modifier
        .padding(start = (treeListElement.level * 12).dp),
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically
    ) {
      if (treeListElement.hasChildren) {
        ButtonWithArrow(
          treeListElement.isExpanded,
          onClick = { updateTreeState.invoke(flatList.indexOf(treeListElement), !treeListElement.isExpanded) }
        )
      } else {
        IconButton(
          modifier = Modifier.size(24.dp),
          onClick = {},
        ) {
          Icon(
            Icons.Sharp.List,
            contentDescription = "Leaf"
          )
        }
      }
      Text(treeListElement.name)
    }
  }
}

@Composable
fun ButtonWithArrow(expanded: Boolean, onClick: () -> Unit) {
  IconButton(
    modifier = Modifier.size(24.dp),
    onClick = onClick
  ) {
    Icon(
      if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowRight,
      contentDescription = "Arrow Icon",
    )
  }
}

fun main() = application {
  val tree = Node(
    "pvServer", mutableListOf(
      Node(
        "vir", mutableListOf(
          Node("modeFlag"),
          Node("vTimer")
        )
      ),
      Node("td")
    )
  )
  val flatList = tree.getFlatList(tree)
  flatList.forEach {
    println("$it ${it.isVisibleInList(flatList)}")
  }
  Window(onCloseRequest = ::exitApplication) {
    MaterialTheme {
      Tree(tree)
    }
  }
}
