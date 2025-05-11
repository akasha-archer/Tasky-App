package com.example.taskyapplication.agenda.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography


// delete bottom sheet
@Composable
fun TaskDeleteBottomSheet(
    onDeleteTask: () -> Unit = {},
    onCancelDelete: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(
                topEnd = 30.dp,
                topStart = 30.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(color = Color.Black)
                .height(204.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(color = taskyColors.surface)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
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
                        onClick = onCancelDelete,
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
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteTaskPreview() {
    TaskDeleteBottomSheet()
}
