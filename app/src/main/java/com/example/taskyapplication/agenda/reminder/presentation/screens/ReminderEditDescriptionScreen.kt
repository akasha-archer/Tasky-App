package com.example.taskyapplication.agenda.reminder.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.presentation.components.EditInputHeader
import com.example.taskyapplication.agenda.reminder.SharedReminderViewModel
import com.example.taskyapplication.agenda.reminder.presentation.ReminderUiState
import com.example.taskyapplication.agenda.task.SharedTaskViewModel
import com.example.taskyapplication.agenda.task.presentation.TaskUiState
import com.example.taskyapplication.main.presentation.components.TaskyScaffold
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun ReminderEditDescriptionRoot(
    modifier: Modifier = Modifier,
    onClickSave: () -> Unit,
    onClickCancel: () -> Unit,
    reminderViewModel: SharedReminderViewModel
) {
    val uiState by reminderViewModel.reminderUiState.collectAsStateWithLifecycle()
    TaskyScaffold(
        modifier = modifier,
        mainContent = { paddingValues ->
            ReminderEditDescription(
                modifier = Modifier
                    .padding(paddingValues),
                state = uiState,
                onAction = { action ->
                    when (action) {
                        is AgendaItemAction.SetDescription -> onClickSave()
                        is AgendaItemAction.CancelEdit -> onClickCancel()
                        else -> {
                            Unit
                        }
                    }
                    reminderViewModel.executeActions(action)
                }
            )
        }
    )
}

@Composable
fun ReminderEditDescription(
    modifier: Modifier = Modifier,
    onAction: (AgendaItemAction) -> Unit,
    state: ReminderUiState,
) {
    var tempDescription by remember { mutableStateOf(state.description) }
    val focusManager = LocalFocusManager.current
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
                    itemToEdit = "Description",
                    onClickSave = {
                        onAction(AgendaItemAction.SetDescription(tempDescription))
                    },
                    onClickCancel = {
                        onAction(AgendaItemAction.CancelEdit)
                    }
                )
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
                    value = tempDescription,
                    onValueChange = {
                        tempDescription = it
                    },
                    textStyle = TaskyTypography.bodyLarge.copy(color = taskyColors.primary),
                    colors = TextFieldDefaults.colors(
                        cursorColor = taskyColors.primary,
                        focusedTextColor = taskyColors.onPrimary,
                        unfocusedTextColor = taskyColors.onPrimary,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ReminderEditDescriptionPreview() {
    ReminderEditDescription(
        state = ReminderUiState(),
        onAction = {},
    )
}
