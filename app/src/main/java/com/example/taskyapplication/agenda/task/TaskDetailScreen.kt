package com.example.taskyapplication.agenda.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskyapplication.agenda.presentation.AgendaEnums
import com.example.taskyapplication.agenda.presentation.ReminderOptions
import com.example.taskyapplication.agenda.presentation.components.BaseDetailScreen
import com.example.taskyapplication.agenda.presentation.components.DetailScreenHeader
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    taskDescription: String,
    taskTitle: String,
    taskDate: String = LocalDate.now().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    ),
    taskTime: String= LocalTime.now().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    ),
    taskReminderTime: String = ReminderOptions.THIRTY_MINUTES_BEFORE.value,
    onClickEdit: () -> Unit = {}
) {
    BaseDetailScreen(
        modifier = modifier,
        screenHeading = {
            DetailScreenHeader(
                onClickEdit = { onClickEdit() }
            )
        },
        agendaItemType = AgendaEnums.TASK.name,
        agendaItemTitle = taskTitle,
        agendaItemDescription = taskDescription,
        taskTime = taskTime,
        taskDate = taskDate,
        taskReminderTime = taskReminderTime
    )
}


@Composable
fun TaskEditScreen(
modifier: Modifier = Modifier,
) {
    // navigation: pop back to Detail screen
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    TaskDetailScreen(
        taskDescription = "Task Description",
        taskTitle = "Task Title",
        taskTime = LocalTime.now().format(
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        ),
        taskDate = LocalDate.now().format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        ),
        taskReminderTime = ReminderOptions.THIRTY_MINUTES_BEFORE.value
    )
}