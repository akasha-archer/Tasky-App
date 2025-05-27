package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun AgendaItemDeleteTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    itemToDelete: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp),
            color = taskyColors.surfaceContainerHigh,
            thickness = 1.dp,
        )
        TextButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Delete $itemToDelete".uppercase(),
                style = TaskyTypography.labelSmall,
                color = taskyColors.error
            )
        }
    }
}

// delete bottom sheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteItemBottomSheet(
    modifier: Modifier = Modifier,
    onDeleteTask: () -> Unit = {},
    onCancelDelete: () -> Unit = {}
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onCancelDelete() },
        sheetState = rememberModalBottomSheetState(),
        containerColor = taskyColors.surface,
        content = {
            Column(
                modifier = Modifier
                    .background(color = taskyColors.surface)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Delete task?",
                    style = TaskyTypography.headlineMedium,
                    color = taskyColors.primary
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "This action cannot be reversed",
                    style = TaskyTypography.bodyMedium,
                    color = taskyColors.onSurface
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .height(52.dp)
                            .width(156.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = taskyColors.onSurfaceVariant,
                        ),
                        onClick = onCancelDelete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = taskyColors.surface,
                            contentColor = taskyColors.onSurface,
                        )
                    ) {
                        Text(text = "CANCEL")
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .height(52.dp)
                            .width(156.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = taskyColors.error,
                        ),
                        onClick = onDeleteTask,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = taskyColors.error,
                            contentColor = taskyColors.onPrimary,
                        )
                    ) {
                        Text(text = "DELETE")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteButtonPreview() {
    AgendaItemDeleteTextButton(
        onClick = {},
        itemToDelete = "Task"
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteBottomSheetPreview() {
    DeleteItemBottomSheet()
}