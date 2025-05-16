package com.example.taskyapplication.agenda.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.agenda.data.TaskState
import com.example.taskyapplication.agenda.presentation.components.AgendaScreenDivider
import com.example.taskyapplication.agenda.presentation.components.EditInputHeader
import com.example.taskyapplication.main.components.TaskyBaseScreen
import com.example.taskyapplication.main.components.TaskyScaffold
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun EditTaskTitleRoot(
    modifier: Modifier = Modifier,
    onClickSave: (String) -> Unit = {},
    onClickCancel: () -> Unit = {},
    viewModel: TaskViewModel = hiltViewModel()
) {
    val taskState by viewModel.taskState.collectAsStateWithLifecycle()
    TaskyScaffold(
        modifier = modifier,
        mainContent = {
            EditTaskTitle(
                modifier = modifier,
                taskState = taskState,
                onUpdateTitle = { viewModel.updateTitleText(it) },
                onClickSave = { onClickSave(taskState.taskTitle) },
                onClickCancel = onClickCancel
            )
        }
    )
}

@Composable
fun EditTaskTitle(
    modifier: Modifier = Modifier,
    itemToEdit: String = "Title",
    taskState: TaskState,
    onUpdateTitle: (String) -> Unit = {},
    onClickSave: (String) -> Unit = {},
    onClickCancel: () -> Unit = {},
) {
    TaskyBaseScreen(
        modifier = modifier,
        isAgendaEditScreen = true,
        screenHeader = {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(color = Color.White),
            )
            {
                EditInputHeader(
                    itemToEdit = itemToEdit.uppercase(),
                    onClickSave = {
                        onClickSave(taskState.taskTitle)
                        // save text and reflect changes in title text view
                    },
                    onClickCancel = {
                        onClickCancel()
                    }
                )
                AgendaScreenDivider()
            }
        },
        mainContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
                    .background(color = Color.White),
            ) {
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxSize(),
                    value = taskState.taskTitle,
                    onValueChange = {
                        onUpdateTitle(it)
                    },
                    textStyle = TaskyTypography.bodyLarge.copy(color = taskyColors.primary),
                    colors = textFieldColors(
                        backgroundColor = taskyColors.surface,
                        cursorColor = taskyColors.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun EditTaskTitlePreview() {
    EditTaskTitle(
        taskState = TaskState(),
    )
}