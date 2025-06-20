package com.example.taskyapplication.agenda.items.reminder.presentation

import com.example.taskyapplication.agenda.data.model.ReminderNotificationOption
import java.time.LocalDate
import java.time.LocalTime

data class ReminderUiState(
    val id: String = "",
    val title: String = "Edit your title",
    val description: String = "Edit your description",
    val time: LocalTime = LocalTime.now(),
    val date: LocalDate = LocalDate.now(),
    val remindAt: ReminderNotificationOption = ReminderNotificationOption.THIRTY_MINUTES_BEFORE,
    val isEditingItem: Boolean = false,
    val isEditingDate: Boolean = false,
    val isEditingTime: Boolean = false,
    val isEditingReminder: Boolean = false,
    val isDeletingItem: Boolean = false
)
