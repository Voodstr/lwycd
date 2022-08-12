package ru.voodster.lwycd.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.voodster.lwycd.R

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
            taskEditButtons(
                onTaskAdd = {
                    onTaskAdd()
                    resetScroll()
                },
                onTaskSave = {onTaskSave()},
                onTaskDelete = {onTaskDelete()},
            )
        }
    }

}


@Composable
fun taskEditButtons(
    onTaskAdd: () -> Unit,
    onTaskDelete: () -> Unit,
    onTaskSave: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { onTaskSave() }) {
            Text(text = "Save")
        }
        Button(onClick = {
            onTaskAdd()
        }) {
            Text(text = "Add")
        }
        Button(onClick = { onTaskDelete() }) {
            Text(text = "Delete")
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

