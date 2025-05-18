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
import com.example.taskyapplication.agenda.presentation.ReminderOptions
import com.example.taskyapplication.main.components.TaskyBaseScreen
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaseDetailScreen(
    modifier: Modifier = Modifier,
    agendaItemType: String,
    agendaItemTitle: String,
    agendaItemDescription: String,
    taskTime: String,
    taskDate: String,
    taskReminderTime: String,
    screenHeading: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = { screenHeading() },
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
                                )
                            },
                            agendaItemDescription = {
                                AgendaDescriptionText(
                                    agendaItemDescription = agendaItemDescription,
                                    onClickEdit = {
                                        //navigate to Edit UI
                                    }
                                )
                            },
                            agendaItemStartTime = {
                                AgendaItemDateTimeRow(
                                    onClickDate = {},
                                    onClickTime = {},
                                    dateText = taskDate,
                                    timeText = taskTime
                                )
                            },
                            agendaItemReminderTime = {
                                ReminderTimeRow(
                                    reminderTime = taskReminderTime,
                                    onEditReminderTime = {
                                        // Handle reminder time edit
                                    }
                                )
                            },
                        )
                        AgendaItemDeleteButton(
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .align(Alignment.BottomCenter),
                            onClick = {}
                        )
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BaseDetailScreenPreview() {
    BaseDetailScreen(
        agendaItemType = "Task",
        agendaItemTitle = "Sample Task",
        agendaItemDescription = "This is a sample task description" +
                " \n Second line of description" +
                "\n Third line of description",
        taskTime = "10:00 AM",
        taskDate = "May 16, 2025",
        taskReminderTime = ReminderOptions.THIRTY_MINUTES_BEFORE.value,
    )
}