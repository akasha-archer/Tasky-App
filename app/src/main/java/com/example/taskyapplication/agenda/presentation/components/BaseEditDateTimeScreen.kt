package com.example.taskyapplication.agenda.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.presentation.AgendaItem
import com.example.taskyapplication.main.components.TaskyBaseScreen
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaseEditDateTimeScreen(
    modifier: Modifier = Modifier,
    agendaItemType: String = "",
    agendaItemTitle: String = "",
    agendaItemDescription: String = "",
    onSelectTitleEdit: () -> Unit = {},
    onSelectDescriptionEdit: () -> Unit = {},
    onSelectEditDate: () -> Unit = {},
    onSelectEditTime: () -> Unit = {},
    onSelectReminderTime: () -> Unit = {},
    onSelectDelete: () -> Unit = {},
    onClickSave: () -> Unit = {},
    onClickCancel: () -> Unit = {},
    selectedDate: String = LocalDate.now().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    ),
    selectedTime: String = LocalTime.now().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
    launchDatePicker: (@Composable () -> Unit)? = null,
    launchTimePicker: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
                EditScreenHeader(
                    itemToEdit = "Reminder",
                    onClickSave = {
                        onClickSave()
                    },
                    onClickCancel = {
                        onClickCancel()
                    }
                )
            },
            mainContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxHeight()
                            .padding(
                                horizontal = 16.dp
                            )
                            .padding(top = 48.dp)
                    ) {
                        AgendaItem(
                            agendaItemType = {
                                AgendaIconTextRow(
                                    itemIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.agenda_type_square),
                                            tint = taskyColors.primary,
                                            contentDescription = ""
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            modifier = Modifier.padding(start = 8.dp),
                                            text = agendaItemType.uppercase(),
                                            style = TaskyTypography.labelMedium,
                                            color = taskyColors.onSurface
                                        )
                                    }
                                )
                            },
                            agendaItemTitle = {
                                AgendaTitleRow(
                                    agendaItemTitle = agendaItemTitle,
                                    isEditing = true,
                                    onClickEdit = {
                                        onSelectTitleEdit()
                                    }
                                )
                            },
                            agendaItemDescription = {
                                AgendaDescriptionText(
                                    agendaItemDescription = agendaItemDescription,
                                    isEditing = true,
                                    onClickEdit = {
                                        onSelectDescriptionEdit()
                                    }
                                )
                            },
                            agendaItemStartTime = {
                                EditItemDateTime(
                                    onClickDate = {
                                        onSelectEditDate()
                                    },
                                    onClickTime = {
                                        onSelectEditTime()
                                    },
                                    date = selectedDate,
                                    time = selectedTime
                                )
                            },
                            agendaItemReminderTime = {
                                ReminderTimeRow(
                                    isEditing = true,
                                    onEditReminderTime = {
                                        onSelectReminderTime()
                                    }
                                )
                            },
                        )
                        AgendaItemDeleteButton(
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .align(Alignment.BottomCenter),
                            onClick = {
                                onSelectDelete()
                            }
                        )
                    }
                }
            }
        )
        if (launchDatePicker != null) {
            launchDatePicker()
        }
        if (launchTimePicker != null) {
            launchTimePicker()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BaseDateTimeEditScreenPreview() {
    BaseEditDateTimeScreen(
        agendaItemType = "Task",
        agendaItemTitle = "Sample Task",
        agendaItemDescription = "This is a sample task description" +
                " \n Second line of description" +
                "\n Third line of description",
        selectedDate = LocalDate.now().format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        ),
        selectedTime = LocalTime.now().format(
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        )
    )
}