package com.example.taskyapplication.agenda.task.presentation

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.task.domain.TaskDomainModel

data class TaskUiState(
    val id: String = "",
    val title: String = "New task",
    val description: String = "Task description",
    val time: String = "",
    val date: String = "",
    val remindAt: ReminderOptions = ReminderOptions.THIRTY_MINUTES_BEFORE,
    val isDone: Boolean = false,
    val isEditingItem: Boolean = false
)

fun TaskDomainModel.toTaskUiState() = TaskUiState(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    remindAt = remindAt,
    isDone = isDone
)
