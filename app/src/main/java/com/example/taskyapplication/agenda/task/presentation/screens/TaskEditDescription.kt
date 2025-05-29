package com.example.taskyapplication.agenda.task.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.presentation.components.EditInputHeader
import com.example.taskyapplication.agenda.task.presentation.TaskUiState
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography


@Composable
fun TaskEditDescription(
    modifier: Modifier = Modifier,
    state: TaskUiState
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
                    itemToEdit = "Description",
                    onClickSave = { /*TODO*/ },
                    onClickCancel = { /*TODO*/ }
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
                    value = state.description,
                    onValueChange = {},
                    textStyle = TaskyTypography.bodyLarge.copy(color = taskyColors.onPrimary),
                    colors = TextFieldDefaults.colors(
                        cursorColor = taskyColors.primary,
                        focusedTextColor = taskyColors.onPrimary,
                        unfocusedTextColor = taskyColors.onPrimary,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor =  Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TaskEditDescriptionPreview() {
    TaskEditDescription(
        state = TaskUiState()
    )
}
