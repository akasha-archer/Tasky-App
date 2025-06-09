package com.example.taskyapplication.agenda.items.task.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.items.task.SharedTaskViewModel
import com.example.taskyapplication.agenda.items.task.presentation.TaskUiState
import com.example.taskyapplication.agenda.presentation.components.AgendaDescriptionText
import com.example.taskyapplication.agenda.presentation.components.AgendaIconTextRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItem
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDateTimeRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDeleteTextButton
import com.example.taskyapplication.agenda.presentation.components.AgendaTitleRow
import com.example.taskyapplication.agenda.presentation.components.DeleteItemBottomSheet
import com.example.taskyapplication.agenda.presentation.components.DetailScreenHeader
import com.example.taskyapplication.agenda.presentation.components.ReminderTimeRow
import com.example.taskyapplication.main.presentation.components.TaskyScaffold
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun TaskDetailRoot(
    modifier: Modifier = Modifier,
    onClickEdit: () -> Unit = {},
    onClickClose: () -> Unit = {},
    taskViewModel: SharedTaskViewModel,
) {
    val uiState by taskViewModel.uiState.collectAsStateWithLifecycle()
    TaskyScaffold(
        mainContent = {
            TaskDetailScreen(
                modifier = modifier,
                onAction = { action ->
                    when (action) {
                        AgendaItemAction.LaunchDateTimeEditScreen -> onClickEdit()
                        AgendaItemAction.CloseDetailScreen -> onClickClose()
                        else -> Unit
                    }
                    taskViewModel.executeActions(action)
                },
                state = uiState,
            )
        }
    )
}

@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    agendaItem: String = "Task",
    onAction: (AgendaItemAction) -> Unit = {},
    state: TaskUiState,
    isEditScreen: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
                DetailScreenHeader(
                    onClickEdit = {
                        onAction(AgendaItemAction.LaunchDateTimeEditScreen)
                    },
                    onClickClose = {
                        onAction(AgendaItemAction.CloseDetailScreen)
                    }
                )
            },
            mainContent = {
                var showDeleteBottomSheet by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .padding(top = 48.dp)
                    ) {
                        AgendaItem(
                            modifier = modifier.fillMaxWidth(),
                            agendaItemType = {
                                AgendaIconTextRow(
                                    itemIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.CheckCircle, // Or another square icon
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(end = 12.dp)
                                                .height(24.dp)
                                                .width(24.dp),
                                            tint = Color.Blue
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            text = agendaItem.uppercase(),
                                            style = TaskyTypography.labelMedium,
                                            color = taskyColors.onSurface
                                        )
                                    },
                                )
                            },
                            agendaItemTitle = {
                                AgendaTitleRow(
                                    agendaItemTitle = state.title,
                                    isEditing = isEditScreen,
                                )
                            },
                            agendaItemDescription = {
                                AgendaDescriptionText(
                                    agendaItemDescription = state.description,
                                    isEditing = isEditScreen,
                                )
                            },
                            agendaItemStartTime = {
                                AgendaItemDateTimeRow(
                                    isEditing = isEditScreen,
                                    timeText = state.time,
                                    dateText = state.date,
                                )
                            },
                            agendaItemReminderTime = {
                                ReminderTimeRow(
                                    reminderTime = state.remindAt.timeString,
                                    isEditing = isEditScreen,
                                )
                            }
                        )
                        AgendaItemDeleteTextButton(
                            modifier = Modifier
                                .padding(bottom = 36.dp)
                                .align(Alignment.BottomEnd),
                            onClick = {
                                showDeleteBottomSheet = true
                            },
                            itemToDelete = agendaItem.uppercase(),
                            isEnabled = state.id.isNotEmpty()
                        )
                        if (showDeleteBottomSheet) {
                            DeleteItemBottomSheet(
                                modifier = Modifier,
                                isLoading = state.isDeletingItem,
                                isButtonEnabled = !state.isDeletingItem,
                                onDeleteTask = {
                                    onAction(AgendaItemAction.DeleteItem(state.id))
                                    showDeleteBottomSheet = false
                                },
                                onCancelDelete = {
                                    showDeleteBottomSheet = false
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BaseDetailScreenPreview() {
    TaskDetailRoot(
        taskViewModel = hiltViewModel(),
    )
}