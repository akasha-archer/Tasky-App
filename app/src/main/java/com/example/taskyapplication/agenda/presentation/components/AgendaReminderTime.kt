package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.data.model.reminderTimeList
import com.example.taskyapplication.agenda.task.presentation.TaskUiState.Companion.REMINDER_THIRTY_MINUTES_BEFORE
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun ReminderTimeRow(
    modifier: Modifier = Modifier,
    reminderTime: String = REMINDER_THIRTY_MINUTES_BEFORE,
    onClickDropDown: () -> Unit = {},
    isEditing: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AgendaIconTextRow(
            itemIcon = {
                Icon(
                    modifier = modifier
                        .alpha(0.7f)
                        .drawBehind {
                            drawRoundRect(
                                color = Color(0xFFF2F3F7),
                                cornerRadius = CornerRadius(3.dp.toPx())
                            )
                        }
                        .padding(horizontal = 4.dp, vertical = 10.dp),
                    imageVector = Icons.Outlined.Notifications,
                    tint = taskyColors.onSurfaceVariant,
                    contentDescription = "Reminder Icon"
                )
            },
            textItem = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = reminderTime,
                    style = TaskyTypography.bodyMedium,
                    color = taskyColors.onSurface
                )
            }
        )
        if (isEditing) {
            Icon(
                modifier = Modifier
                    .clickable { onClickDropDown() }
                    .padding(end = 8.dp),
                painter = painterResource(R.drawable.dropdown),
                tint = taskyColors.primary,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ReminderDropDown(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    onTimeSelected: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var selectedTime by rememberSaveable { mutableStateOf(reminderTimeList[0].reminderTime) }
    DropdownMenu(
        modifier = modifier
            .fillMaxWidth()
            .background(taskyColors.surface),
        expanded = isExpanded,
        onDismissRequest = {
            onDismiss()
        },
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        content = {
            reminderTimeList.forEachIndexed { index, time ->
                DropdownMenuItem(
                    onClick = {
                        selectedTime = time.reminderTime
                        onTimeSelected(selectedTime)
                    },
                    text = {
                        Text(text = time.reminderTime)
                    },
                    trailingIcon = {
                        val isSelectedRow = selectedTime == time.reminderTime
                        if (isSelectedRow) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 8.dp),
                                imageVector = Icons.Filled.Check,
                                tint = taskyColors.validInput,
                                contentDescription = ""
                            )
                        }
                    }
                )
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun ReminderPreview() {
    ReminderTimeRow(
        isEditing = true
    )
}

@Composable
@Preview(showBackground = true)
fun ReminderDropDownPreview() {
    ReminderDropDown(
        onDismiss = {},
        isExpanded = true
    )
}
