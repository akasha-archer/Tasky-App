package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun ReminderTimeRow(
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
    reminderTime: String = "30 minutes before",
    onEditReminderTime: () -> Unit
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
                    .clickable { onEditReminderTime() }
                    .padding(end = 8.dp),
                painter = painterResource(R.drawable.dropdown),
                tint = taskyColors.primary,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ReminderDropDownMenu(
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        modifier = modifier
            .background(color = Color.Green)
            .fillMaxSize(),
        expanded = false,
        onDismissRequest = { /*TODO*/ },
        shadowElevation = 8.dp,
        containerColor = taskyColors.surface,
        content = {
            ReminderDropDownItem(
                reminderText = "10 minutes before",
            )
            ReminderDropDownItem(
                reminderText = "30 minutes before",
            )
            ReminderDropDownItem(
                reminderText = "1 hour before",
            )
            ReminderDropDownItem(
                reminderText = "6 hours before",
            )
            ReminderDropDownItem(
                reminderText = "1 day before",
            )
        }
    )
}

@Composable
fun ReminderDropDownItem(
    modifier: Modifier = Modifier,
    reminderText: String = "30 minutes before",
    isSelected: Boolean = false
) {
    val background = taskyColors.surfaceContainerHigh
    DropdownMenuItem(
        text = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = reminderText,
                style = TaskyTypography.bodyMedium,
                color = taskyColors.primary
            )
        },
        onClick = { /*TODO*/ },
        modifier = modifier
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
        isEditing = true,
        onEditReminderTime = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ReminderDropDownPreview() {
    ReminderDropDownMenu()
}
