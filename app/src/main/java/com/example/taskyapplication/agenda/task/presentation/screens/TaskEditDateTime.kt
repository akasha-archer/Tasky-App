package com.example.taskyapplication.agenda.task.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_ONE_DAY_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_ONE_HOUR_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_SIX_HOURS_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_TEN_MINUTES_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_THIRTY_MINUTES_BEFORE
import com.example.taskyapplication.agenda.domain.getReminderOption
import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.presentation.components.AgendaDescriptionText
import com.example.taskyapplication.agenda.presentation.components.AgendaIconTextRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItem
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDateTimeRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDeleteTextButton
import com.example.taskyapplication.agenda.presentation.components.AgendaTitleRow
import com.example.taskyapplication.agenda.presentation.components.EditScreenHeader
import com.example.taskyapplication.agenda.presentation.components.ReminderDropDown
import com.example.taskyapplication.agenda.presentation.components.ReminderTimeRow
import com.example.taskyapplication.agenda.presentation.components.TaskyDatePicker
import com.example.taskyapplication.agenda.presentation.components.TaskyTimePicker
import com.example.taskyapplication.agenda.task.SharedTaskViewModel
import com.example.taskyapplication.agenda.task.presentation.TaskUiState
import com.example.taskyapplication.main.presentation.components.TaskyScaffold
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun TaskEditDateTimeRoot(
    modifier: Modifier = Modifier,
    onClickSave: () -> Unit,
    onClickCancel: () -> Unit,
    onSelectEditTitle: () -> Unit,
    onSelectEditDescription: () -> Unit,
    taskViewModel: SharedTaskViewModel
) {
    val uiState by taskViewModel.uiState.collectAsStateWithLifecycle()
    TaskyScaffold(
        modifier = modifier,
        mainContent = {
            TaskEditDateTimeScreen(
                modifier = Modifier,
                state = uiState,
                onAction = { action ->
                    when(action) {
                        AgendaItemAction.SaveDateTimeEdit -> onClickSave()
                        AgendaItemAction.CancelEdit -> onClickCancel()
                        AgendaItemAction.LaunchEditTitleScreen -> onSelectEditTitle()
                        AgendaItemAction.LaunchEditDescriptionScreen -> onSelectEditDescription()
                        else -> {}
                    }
                    taskViewModel.executeActions(action)
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditDateTimeScreen(
    modifier: Modifier = Modifier,
    state: TaskUiState,
    onAction: (AgendaItemAction) -> Unit,
    isEditScreen: Boolean = true
) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(
        is24Hour = false
    )
    val timeOfDay = if (timePickerState.isAfternoon) "PM" else "AM"

    // if user changed the date or time, but cancels the edit,
    // we want to display the values that were previously entered
    val previousDate = state.date
    val previousTime = state.time
    val previousReminder = state.remindAt

    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
                EditScreenHeader(
                    itemToEdit = "Task",
                    onClickSave = {
                        onAction(AgendaItemAction.SaveDateTimeEdit)
                    },
                    onClickCancel = {
                        onAction(AgendaItemAction.CancelEdit)
                        onAction(AgendaItemAction.SetTime(previousTime))
                        onAction(AgendaItemAction.SetDate(previousDate))
                        onAction(AgendaItemAction.SetReminderTime(previousReminder))
                    }
                )
            },
            mainContent = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp)
                            .padding(top = 48.dp)
                    ) {
                        AgendaItem(
                            modifier = modifier.fillMaxSize(),
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
                                            text = "TASK",
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
                                    onClickEdit = {
                                        onAction(AgendaItemAction.LaunchEditTitleScreen)
                                    }
                                )
                            },
                            agendaItemDescription = {
                                AgendaDescriptionText(
                                    agendaItemDescription = state.description,
                                    isEditing = isEditScreen,
                                    onClickEdit = {
                                        onAction(AgendaItemAction.LaunchEditDescriptionScreen)
                                    }
                                )
                            },
                            agendaItemStartTime = {
                                AgendaItemDateTimeRow(
                                    dateText = state.date.ifEmpty { previousDate },
                                    timeText = state.time.ifEmpty { previousTime },
                                    onClickTime = {
                                        onAction(AgendaItemAction.ShowTimePicker)
                                    },
                                    onClickDate = {
                                        onAction(AgendaItemAction.ShowDatePicker)
                                    },
                                    isEditing = isEditScreen,
                                )
                            },
                            agendaItemReminderTime = {
                                Box(
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    ReminderTimeRow(
                                        reminderTime = state.remindAt.timeString.ifEmpty { ReminderOptions.THIRTY_MINUTES_BEFORE.timeString },
                                        isEditing = isEditScreen,
                                        onClickDropDown = {
                                            onAction(AgendaItemAction.ShowReminderDropDown)
                                        }
                                    )
                                    if (state.isEditingReminder) {
                                        ReminderDropDown(
                                            modifier = Modifier
                                                .padding(start = 36.dp, end = 16.dp),
                                            onDismiss = {
                                                onAction(AgendaItemAction.HideReminderDropDown)
                                            },
                                            isExpanded = true,
                                            onTimeSelected = { time ->
                                                onAction(AgendaItemAction.SetReminderTime(getReminderOption(time)))
                                            },
                                        )
                                    }
                                }
                            },
                            launchDatePicker = {
                                if (state.isEditingDate) {
                                    TaskyDatePicker(
                                        datePickerState = datePickerState,
                                        onDismiss = {
                                            onAction(AgendaItemAction.HideDatePicker)
                                        },
                                        onConfirm = {
                                            onAction(
                                                AgendaItemAction.SetDate(
                                                    datePickerState.selectedDateMillis?.toDateAsString()
                                                        ?: state.date
                                                )
                                            )
                                        },
                                    )
                                }
                            },
                            launchTimePicker = {
                                if (state.isEditingTime) {
                                    TaskyTimePicker(
                                        timePickerState = timePickerState,
                                        onDismiss = {
                                            onAction(AgendaItemAction.HideTimePicker)
                                        },
                                        onConfirm = {
                                            onAction(
                                                AgendaItemAction.SetTime(
                                                    timePickerState.hour.toString() + ":" + timePickerState.minute.toString() + " $timeOfDay"
                                                )
                                            )
                                        },
                                    )
                                }
                            },
                        )
                        AgendaItemDeleteTextButton(
                            modifier = Modifier
                                .padding(bottom = 36.dp)
                                .align(Alignment.BottomEnd),
                            onClick = {},
                            itemToDelete = "Task".uppercase()
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditTimeScreenPreview() {
    TaskEditDateTimeScreen(
        state = TaskUiState(),
        onAction = {},
    )
}
