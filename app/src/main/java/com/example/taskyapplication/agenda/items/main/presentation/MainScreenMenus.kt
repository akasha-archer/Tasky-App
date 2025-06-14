package com.example.taskyapplication.agenda.items.main.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun MenuText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = taskyColors.primary
) {
    Text(
        modifier = modifier,
        text = text,
        style = TaskyTypography.bodyMedium.copy(color = color)
    )
}

@Composable
fun MainScreenLogoutMenu(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    isExpanded: Boolean = false
) {
    DropdownMenu(
        modifier = modifier
            .padding(vertical = 4.dp),
        containerColor = taskyColors.surface,
        shape = RoundedCornerShape(12.dp),
        expanded = isExpanded,
        shadowElevation = 8.dp,
        onDismissRequest = onDismissRequest,
        content = {
            DropdownMenuItem(
                onClick = { onLogoutClick() },
                text = {
                    MenuText(
                        modifier = Modifier
                            .padding(end = 16.dp),
                        text = "Log out",
                        color = taskyColors.error
                    )
                }
            )
        }
    )
}

@Composable
fun FabPopupMenu(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    navigateToScreen: (AgendaItemType) -> Unit,
    isExpanded: Boolean = false
) {
    DropdownMenu(
        modifier = modifier
            .padding(12.dp),
        containerColor = taskyColors.surface,
        shape = RoundedCornerShape(8.dp),
        expanded = isExpanded,
        shadowElevation = 8.dp,
        onDismissRequest = onDismissRequest,
        content = {
            DropdownMenuItem(
                onClick = { navigateToScreen(AgendaItemType.EVENT) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Create new Event",
                        tint = taskyColors.onSurfaceVariant
                    )
                },
                text = {
                    MenuText(text = "Event")
                }
            )

            DropdownMenuItem(
                onClick = { navigateToScreen(AgendaItemType.TASK) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "Create new Task",
                        tint = taskyColors.onSurfaceVariant
                    )
                },
                text = {
                    MenuText(text = "Task")
                }
            )

            DropdownMenuItem(
                onClick = { navigateToScreen(AgendaItemType.REMINDER) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Create new Event",
                        tint = taskyColors.onSurfaceVariant
                    )
                },
                text = {
                    MenuText(text = "Reminder")
                }
            )
        }
    )
}

@Composable
fun CardDropDownMenu(
    modifier: Modifier = Modifier,
    onOpenClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    isExpanded: Boolean = false
) {
    DropdownMenu(
        modifier = modifier
            .padding(12.dp),
        containerColor = taskyColors.surface,
        shape = RoundedCornerShape(8.dp),
        expanded = isExpanded,
        shadowElevation = 8.dp,
        onDismissRequest = onDismissRequest,
        content = {
            DropdownMenuItem(
                onClick = { onOpenClick() },
                text = {
                    MenuText(text = "Open")
                }
            )
            DropdownMenuItem(
                onClick = { onEditClick() },
                text = {
                    MenuText(text = "Edit")
                }
            )
            DropdownMenuItem(
                onClick = { onDeleteClick() },
                text = {
                    MenuText(text = "Delete")
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CardDropDownMenuPreview() {
    CardDropDownMenu(
        onOpenClick = {},
        onEditClick = {},
        onDeleteClick = {},
        isExpanded = true
    )
}

@Preview(showBackground = true)
@Composable
fun FabDropDownMenuPreview() {
   FabPopupMenu(
       isExpanded = true,
       navigateToScreen = {},
   )
}

@Preview(showBackground = true)
@Composable
fun LogoutMenuPreview() {
    MainScreenLogoutMenu(
        onLogoutClick = {},
        isExpanded = true
    )
}