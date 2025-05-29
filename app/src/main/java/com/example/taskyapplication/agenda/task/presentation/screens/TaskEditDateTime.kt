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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.data.model.AgendaItemType
import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.presentation.components.AgendaDescriptionText
import com.example.taskyapplication.agenda.presentation.components.AgendaIconTextRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItem
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDateTimeRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDeleteTextButton
import com.example.taskyapplication.agenda.presentation.components.AgendaTitleRow
import com.example.taskyapplication.agenda.presentation.components.EditScreenHeader
import com.example.taskyapplication.agenda.presentation.components.ReminderTimeRow
import com.example.taskyapplication.agenda.presentation.components.TaskyDatePicker
import com.example.taskyapplication.agenda.presentation.components.TaskyTimePicker
import com.example.taskyapplication.agenda.task.presentation.TaskUiState
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditDateTimeScreen(
    modifier: Modifier = Modifier,
    onAction: (AgendaItemAction) -> Unit,
    agendaItem: AgendaItemType,
    state: TaskUiState,
    isEditScreen: Boolean = true
) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
                EditScreenHeader(
                    itemToEdit = "Task",
                    onClickSave = {

                    },
                    onClickCancel = {

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
                                            text = agendaItem.name.uppercase(),
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
                                ReminderTimeRow(
                                    reminderTime = state.time.ifEmpty { ReminderOptions.THIRTY_MINUTES_BEFORE.timeString },
                                    isEditing = isEditScreen,
                                )
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
                                                        ?: ""
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
                                                    timePickerState.hour.toString() + ":" + timePickerState.minute.toString()
                                                )
                                            )
                                        },
                                    )
                                }
                            }
                        )
                        AgendaItemDeleteTextButton(
                            modifier = Modifier
                                .padding(bottom = 36.dp)
                                .align(Alignment.BottomEnd),
                            onClick = {},
                            itemToDelete = AgendaItemType.TASK.name
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
        agendaItem = AgendaItemType.TASK,
        state = TaskUiState(),
        onAction = {},
    )
}
