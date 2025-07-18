package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun AgendaItemDeleteTextButton(
    itemToDelete: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean = false
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
            enabled = isEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .alpha(if (isEnabled) 1f else 0.4f)
        ) {
            Text(
                text = stringResource(R.string.delete_text_button, itemToDelete).uppercase(),
                style = TaskyTypography.labelSmall,
                color = if (isEnabled) taskyColors.error else taskyColors.inputText
            )
        }
    }
}

// delete bottom sheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteItemBottomSheet(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isButtonEnabled: Boolean = false,
    onDeleteTask: () -> Unit = {},
    onCancelDelete: () -> Unit = {},
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
                    text = stringResource(R.string.delete_bottom_sheet_heading),
                    style = TaskyTypography.headlineMedium,
                    color = taskyColors.primary
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.delete_bottom_sheet_sub_heading),
                    style = TaskyTypography.bodyMedium,
                    color = taskyColors.onSurface
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f),
                        border = BorderStroke(
                            width = 1.dp,
                            color = taskyColors.onSurfaceVariant,
                        ),
                        onClick = onCancelDelete,
                        enabled = isButtonEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = taskyColors.surface,
                            contentColor = taskyColors.onSurface,
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 12.dp),
                            text = stringResource(android.R.string.cancel).uppercase(),
                            style = TaskyTypography.labelMedium
                        )
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f),
                        border = BorderStroke(
                            width = 1.dp,
                            color = taskyColors.error,
                        ),
                        onClick = onDeleteTask,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = taskyColors.error,
                            contentColor = taskyColors.onPrimary,
                            disabledContainerColor = taskyColors.onSurface.copy(alpha = 0.7f),
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(36.dp)
                                    .alpha(if (isLoading) 1f else 0f),
                                strokeWidth = 1.5.dp,
                                color = taskyColors.onPrimary
                            )
                            Text(
                                modifier = Modifier
                                    .alpha(if (isLoading) 0f else 1f)
                                    .padding(vertical = 12.dp),
                                text = stringResource(R.string.delete_bottom_sheet_button).uppercase(),
                                style = TaskyTypography.labelMedium,
                            )
                        }
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