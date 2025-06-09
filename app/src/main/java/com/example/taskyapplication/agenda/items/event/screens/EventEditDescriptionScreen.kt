package com.example.taskyapplication.agenda.items.event.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState
import com.example.taskyapplication.agenda.presentation.components.EditInputHeader
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun EventEditDescriptionScreen(
    modifier: Modifier = Modifier,
    onAction: (AgendaItemAction) -> Unit = {},
    state: EventUiState
) {
    var tempTitle by remember { mutableStateOf(state.title) }
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
                        onAction(AgendaItemAction.SetTitle(tempTitle))
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
                    value = tempTitle,
                    onValueChange = {
                        tempTitle = it
                    },
                    textStyle = TaskyTypography.bodyLarge
                        .copy(
                            color = taskyColors.primary,
                            textAlign = TextAlign.Left
                        ),
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
fun EventEditDescriptionPreview() {
    EventEditDescriptionScreen(
        state = EventUiState()
    )
}