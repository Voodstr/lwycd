@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package ru.voodster.lwycd.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.voodster.lwycd.CheckListFolderState
import ru.voodster.lwycd.CheckableTask
import ru.voodster.lwycd.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListContent(
    modifier: Modifier,
    checkListState: CheckListFolderState
) {
    val scrollState = rememberLazyListState()

    val scope = rememberCoroutineScope()
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                Checklist(
                    checklist = checkListState.tasks,
                    scrollState = scrollState,
                    modifier = Modifier.weight(1f)
                )
                TaskEdit(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding(),
                    onTaskSave = {},
                    onTaskAdd = {text ->
                        checkListState.addTask(
                            CheckableTask(
                                text = text,
                                completion = false,
                                folderID = checkListState.folder.id
                            )
                        )
                    },
                    onTaskDelete = {}
                ) {
                    scope.launch {
                        scrollState.scrollToItem(checkListState.tasks.size)
                    }
                }
            }
            TaskFolderNameBar(
                taskFolderName = "FolderName",
                onBackPressed = {},
                scrollBehavior = scrollBehavior,
                modifier = Modifier.statusBarsPadding()
            )
        }
    }

}


@Composable
fun Checklist(
    checklist: List<CheckableTask>,
    scrollState: LazyListState,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        LazyColumn(
            state = scrollState,
            contentPadding = WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                .asPaddingValues(),
            modifier = modifier.fillMaxSize()
        ) {
            checklist.forEach {
                item { TaskRow(checkableTask = it) }
            }
        }
    }
}

@Composable
fun TaskRow(checkableTask: CheckableTask) {
    var viewedText by remember { mutableStateOf(checkableTask.text) }
    var viewedCheck by remember { mutableStateOf(checkableTask.completion) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = viewedCheck,
            onCheckedChange = {
                viewedCheck = it
                checkableTask.completion = it
            })
        BasicTextField(value = viewedText, singleLine = true,
            onValueChange = {
                viewedText = it
                checkableTask.text = it
            })
    }
}

@Composable
fun TaskFolderNameBar(
    taskFolderName: String,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    var functionalityNotAvailablePopupShown by remember { mutableStateOf(false) }
    if (functionalityNotAvailablePopupShown) {
        FunctionalityNotAvailablePopup { functionalityNotAvailablePopupShown = false }
    }

    LwycdAppBar(
        title = {
            Text(text = taskFolderName, style = MaterialTheme.typography.titleMedium)
        },
        actions = {

        },
        onBackPressed = onBackPressed,
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
fun FunctionalityNotAvailablePopup(onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            androidx.compose.material3.Text(
                text = "Functionality not available \uD83D\uDE48",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                androidx.compose.material3.Text(text = "CLOSE")
            }
        }
    )
}


@Composable
fun LwycdAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onBackPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    ).value
    val foregroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )
    Box(modifier = Modifier.background(backgroundColor)) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            actions = actions,
            title = title,
            scrollBehavior = scrollBehavior,
            colors = foregroundColors,
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        )
    }
}
