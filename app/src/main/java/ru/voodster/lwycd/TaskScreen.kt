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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskScreen(checklistViewModel: ChecklistViewModel) {
    val tasks = checklistViewModel.checklist.collectAsState()
    Scaffold(
        topBar = {
            TaskScreenTopAppBar(
                "folderName",
                onBackPressed = { checklistViewModel.onBackToFolders() })
        },
        floatingActionButton = {
            AddFab(onFabAdd = {
                checklistViewModel.addTask(CheckableTask("new task", false))
            })
        }
    ) {
        TaskColumn(
            tasks = tasks.value,
            Modifier
                .padding(it)
        )
    }
}


@Composable
fun UserInput(
    onTaskEdited: () -> Unit,
    resetScroll: () -> Unit,
    modifier: Modifier
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }
    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }


}


@Preview
@Composable
fun InputFieldPreview() {
    UserInputField()
}

@Composable
fun UserInputField(textFieldValue: TextFieldValue, keyboardType: KeyboardType = KeyboardType.Text,
                   onTextChanged: (TextFieldValue) -> Unit,
                   keyboardShown: Boolean,
                   onTextFieldFocused: (Boolean) -> Unit,
) {
    Row  (      modifier = Modifier
            .fillMaxWidth()
        .height(64.dp)
        .semantics {
            contentDescription = "UserInputField"
            keyboardShownProperty = keyboardShown
        },
    horizontalArrangement = Arrangement.End
    ) {
        TextField(value = textFieldValue, onValueChange = {onTextChanged(it)})
    }
}


@Composable
fun TaskColumn(tasks: List<CheckableTask>, modifier: Modifier) {
    LazyColumn(modifier = modifier.imePadding(), content = {
        items(tasks) { task ->
            TaskRow(task)
        }
    })
}

@Composable
fun TaskRow(youCanDo: CheckableTask) {
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
/*
@Composable
@Preview
fun TaskScreenPreview() {
    val tasksFromFolder = arrayListOf(
        Task("get up", false),
        Task("brush teeth", false),
        Task("boil eggs", false),
        Task("make coffee", false),
        Task("eat breakfast", false),
        Task("dress up", false),
        Task("go to work", false),
    )
    TaskScreen()
}
 */
