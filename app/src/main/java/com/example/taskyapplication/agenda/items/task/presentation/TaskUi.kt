package com.example.taskyapplication.agenda.items.task.presentation

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.items.task.domain.TaskDto
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class TaskUiState(
    val id: String = "",
    val title: String = "New task",
    val description: String = "Task description",
    val time: String = LocalTime.now().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    ),
    val date: String = LocalDate.now().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
    val remindAt: ReminderOptions = ReminderOptions.THIRTY_MINUTES_BEFORE,
    val isDone: Boolean = false,
    val isEditingItem: Boolean = false,
    val isEditingDate: Boolean = false,
    val isEditingTime: Boolean = false,
    val isEditingReminder: Boolean = false
)

fun TaskDto.toTaskUiState() = TaskUiState(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    remindAt = remindAt,
    isDone = isDone
)
