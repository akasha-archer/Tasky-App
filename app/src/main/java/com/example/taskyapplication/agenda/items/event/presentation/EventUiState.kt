package com.example.taskyapplication.agenda.items.event.presentation

import android.net.Uri
import com.example.taskyapplication.agenda.data.model.ReminderOptions
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class EventUiState(
    val id: String = "",
    val title: String = "Edit your title",
    val description: String = "Edit your description",
    val photos: List<Uri> = emptyList(),
    val startTime: String = LocalTime.now().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
    val startDate: String = LocalDate.now().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
    val endTime: String = LocalTime.now().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
    val endDate: String = LocalDate.now().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
    val remindAt: ReminderOptions = ReminderOptions.THIRTY_MINUTES_BEFORE,
    val isEditingItem: Boolean = false,
    val isEditingDate: Boolean = false,
    val isEditingTime: Boolean = false,
    val isEditingEvent: Boolean = false,
    val isEditingReminder: Boolean = false
)
