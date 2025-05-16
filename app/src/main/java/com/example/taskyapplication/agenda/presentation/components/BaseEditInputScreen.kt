package com.example.taskyapplication.agenda.presentation.components

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
import com.example.taskyapplication.main.components.TaskyScaffold
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun BaseEditInput(
    modifier: Modifier = Modifier,
    itemToEdit: String = "Reminder",
    textValue: String = "",
    onClickSave: () -> Unit = {},
    onClickCancel: () -> Unit = {}
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
                    onClickSave = {
                        // save text and reflect changes in relevant text view
                    },
                    onClickCancel = {
                        // don't save changes
                        // return to detail screen
                    }
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
                TextField(
                    value = textValue,
                    onValueChange = {
                    },
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxSize(),
                    textStyle = TaskyTypography.bodyLarge,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = taskyColors.surface,
                        focusedContainerColor = taskyColors.surface,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = taskyColors.primary,
                        focusedTextColor = taskyColors.primary
                    )
                ) // end text field
            } // end main content column
        } // end main content
    )
}


@Preview(showBackground = true)
@Composable
fun BaseEditInputPreview() {
    BaseEditInput()
}

