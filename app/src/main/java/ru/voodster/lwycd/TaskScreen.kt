@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package ru.voodster.lwycd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(checklistViewModel: ChecklistViewModel,modifier: Modifier) {
    val tasks = checklistViewModel.checklist.collectAsState()
    val currentFolderID = 1

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TaskScreenTopAppBar(
                "folderName",
                onBackPressed = { checklistViewModel.onBackToFolders() })
        }
    ) {
        Surface(modifier = modifier) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) {
                    TaskColumn(
                        tasks = tasks.value,
                        Modifier
                            .padding(it)
                            .weight(1f)
                            .fillMaxSize()
                    )
                    TaskEdit(
                        onTaskAdd = {checklistViewModel.addTask("New Task",currentFolderID)},
                        onTaskDelete = {},
                        onTaskSave = {},
                        resetScroll = {
                            scope.launch {
                            scrollState.scrollToItem(0)
                        } },
                        modifier = Modifier
                            .navigationBarsPadding()
                            .imePadding()
                            .weight(0.1f))
                }
            }
        }
    }
}

@Composable
fun TaskEdit(
    onTaskSave: (String) -> Unit,
    onTaskAdd: () -> Unit,
    onTaskDelete: () -> Unit,
    resetScroll: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }
    // Used to decide if the keyboard should be shown+
    var textFieldFocusState by remember { mutableStateOf(false) }
    Surface(elevation = 2.dp) {
        Column(modifier = modifier) {
            TaskInputField(
                textFieldValue = textState,
                onTextChanged = { textState = it },
                keyboardShown = textFieldFocusState,
                onTextFieldFocused = { focused ->
                    if (focused) {
                        resetScroll()
                    }
                    textFieldFocusState = focused
                },
                focusState = textFieldFocusState
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { onTaskSave(textState.text) }) {
                    Text(text = "Save")
                }
                Button(onClick = { onTaskAdd() }) {
                    Text(text = "Add")
                    resetScroll()
                }
                Button(onClick = { onTaskDelete() }) {
                    Text(text = "Delete")
                }
            }
        }
    }

}


@Preview
@Composable
fun InputFieldPreview() {
    Scaffold(Modifier.fillMaxSize()) {
        TaskEdit(
            onTaskSave = {},
            onTaskDelete = {},
            onTaskAdd = {},
            resetScroll = { /*TODO*/ },
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@Composable
fun TaskInputField(
    textFieldValue: TextFieldValue,
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (TextFieldValue) -> Unit,
    keyboardShown: Boolean,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .semantics {
                contentDescription = "UserInputField"
                keyboardShownProperty = keyboardShown
            },
        horizontalArrangement = Arrangement.End
    ) {
        Surface(Modifier.padding(2.dp)) {
            Box(
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .align(Alignment.Bottom)
                    .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
            ) {
                var lastFocusState by remember { mutableStateOf(false) }
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { onTextChanged(it) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 32.dp)
                        .align(Alignment.CenterStart)
                        .onFocusChanged { state ->
                            if (lastFocusState != state.isFocused) {
                                onTextFieldFocused(state.isFocused)
                            }
                            lastFocusState = state.isFocused
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Send
                    ),
                    maxLines = 1,
                    cursorBrush = SolidColor(LocalContentColor.current),
                    textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
                )
                if (textFieldValue.text.isEmpty() && !focusState) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 32.dp),
                        text = stringResource(id = R.string.tasktextfield_hint)
                    )
                }
            }

        }
    }
}


@Composable
fun TaskColumn(tasks: List<CheckableTasks>, modifier: Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize(), content = {
        items(tasks) { task ->
            TaskRow(task)
        }
    })
}

@Composable
fun TaskRow(youCanDo: CheckableTasks) {
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
