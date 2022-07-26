package ru.voodster.lwycd

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SimpleColumn(list: MutableList<String>) {
    val rememberedList = remember { mutableStateOf(list) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { list.add("0") }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add)
                )
            }
        }) {
        LazyColumn {
            items(rememberedList.value) {
                Text(text = it)
            }
        }
    }
}

@Composable
fun TaskScreen(folderName: String, folderList: MutableList<YouCanDo>) {
    val myTasks by remember { mutableStateOf(folderList) }
    Scaffold(
        topBar = {
            TaskScreenTopAppBar(folderName, onBackPressed = { backToFolders() })
        },
        floatingActionButton = {
            AddFab(onFabAdd = {
                myTasks.add(YouCanDo("new task",false))
            })
        }
    ) {
        TaskColumn(tasks = myTasks)
    }
}

@Composable
fun TaskColumn(tasks: MutableList<YouCanDo>) {
    val list by remember {
        mutableStateOf(tasks)
    }
    LazyColumn(content = {
        items(list) { task ->
            TaskRow(task)
        }
    })
}

@Composable
fun TaskRow(youCanDo: YouCanDo) {
    var viewedText by remember { mutableStateOf(youCanDo.text) }
    var viewedCheck by remember { mutableStateOf(youCanDo.completion) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = viewedCheck,
            onCheckedChange = {
                viewedCheck = it
                youCanDo.completion = it
            })
        BasicTextField(value = viewedText, singleLine = true,
            onValueChange = {
                viewedText = it
                youCanDo.text = it
            })
    }
}


@Composable
fun AddFab(onFabAdd: () -> Unit) {
    FloatingActionButton(
        onClick = { onFabAdd() },
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(id = R.string.add))
    }
}

fun backToFolders() {

}

@Composable
fun TaskScreenTopAppBar(topAppBarText: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )
}

@Composable
@Preview
fun TaskScreenPreview() {
    val tasksFromFolder = arrayListOf(
        YouCanDo("get up", false),
        YouCanDo("brush teeth", false),
        YouCanDo("boil eggs", false),
        YouCanDo("make coffee", false),
        YouCanDo("eat breakfast", false),
        YouCanDo("dress up", false),
        YouCanDo("go to work", false),
    )
    TaskScreen("Work", tasksFromFolder)
}

data class YouCanDo(
    var text: String,
    var completion: Boolean
)