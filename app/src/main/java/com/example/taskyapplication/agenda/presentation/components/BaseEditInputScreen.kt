package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.main.components.TaskyScaffold
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun BaseEditInput(
    modifier: Modifier = Modifier,
    itemToEdit: String = "Reminder"
) {
    TaskyScaffold(
        modifier = modifier,
        topBar = {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(color = Color.White),
            )
            {
                EditInputHeader(
                    itemToEdit = itemToEdit,
                    onClickSave = { /*TODO*/ },
                    onClickCancel = { /*TODO*/ }
                )
                AgendaScreenDivider()
            }
        },
        mainContent = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.White),
            ) {
                BasicTextField(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxSize(),
                    state = rememberTextFieldState(),
                    textStyle = TaskyTypography.headlineLarge,
                    cursorBrush = SolidColor(taskyColors.primary),
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun BaseEditInputPreview() {
    BaseEditInput()
}

