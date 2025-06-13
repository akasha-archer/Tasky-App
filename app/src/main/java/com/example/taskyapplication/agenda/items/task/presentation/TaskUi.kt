package com.example.taskyapplication.agenda.items.task.presentation

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import java.time.LocalDate
import java.time.LocalTime

data class TaskUiState(
    val id: String = "",
    val title: String = "New task",
    val description: String = "Task description",
    val time: LocalTime = LocalTime.now(),
    val date: LocalDate = LocalDate.now(),
    val remindAt: ReminderOptions = ReminderOptions.THIRTY_MINUTES_BEFORE,
    val isDone: Boolean = false,
    val isEditingItem: Boolean = false,
    val isEditingDate: Boolean = false,
    val isEditingTime: Boolean = false,
    val isEditingReminder: Boolean = false,
    val isDeletingItem: Boolean = false
)
