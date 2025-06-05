package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.data.model.reminderTimeList
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun ReminderTimeRow(
    modifier: Modifier = Modifier,
    reminderTime: String = ReminderOptions.THIRTY_MINUTES_BEFORE.value.toString(),
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
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    imageVector = Icons.Outlined.Notifications,
                    tint = taskyColors.onSurfaceVariant,
                    contentDescription = "Reminder Icon"
                )
            },
            textItem = {
                if (isEditing) {
                    ReminderDropDownMenu(
                        onDismiss = {},
                        isExpanded = false
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = reminderTime,
                        style = TaskyTypography.bodyMedium,
                        color = taskyColors.onSurface
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDropDownMenu(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    selectedReminder: String = reminderTimeList[0].reminderTime,
    onSelectReminderOption: (String) -> Unit = {},
    isExpanded: Boolean = false
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = {}
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                type = MenuAnchorType.PrimaryNotEditable
            ),
            textStyle = TaskyTypography.bodyMedium.copy(
                color = taskyColors.onSurface
            ),
            value = selectedReminder,
            onValueChange = {
                onSelectReminderOption(it)
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onDismiss
        ) {
            reminderTimeList.forEach { time ->
                ReminderDropDownItem(
                    reminderText = time.reminderTime
                )

            }
        }


    }
}

@Composable
fun ReminderDropDownItem(
    modifier: Modifier = Modifier,
    reminderText: String = "30 minutes before",
    onSelectTime: () -> Unit = {},
    isSelected: Boolean = false
) {
    val background = taskyColors.surfaceContainerHigh
    DropdownMenuItem(
        text = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = reminderText,
                style = TaskyTypography.bodyMedium,
                color = taskyColors.onSurface
            )
        },
        onClick = onSelectTime,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .drawBehind {
                drawRect(
                    color = background
                )
            },
        trailingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    tint = taskyColors.validInput,
                    contentDescription = "selected time"
                )
            }
        },
        enabled = true,
        colors = MenuItemColors(
            textColor = taskyColors.primary,
            trailingIconColor = taskyColors.validInput,
            disabledTextColor = taskyColors.primary,
            leadingIconColor = Color.Transparent,
            disabledLeadingIconColor = Color.Transparent,
            disabledTrailingIconColor = Color.Transparent,
        )
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
    ReminderDropDownMenu(
        onDismiss = {},
        isExpanded = false
    )
}
