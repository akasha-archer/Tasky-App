package com.example.taskyapplication.agenda.items.event.presentation

import android.net.Uri
import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class EventUiState(
    val id: String = "",
    val title: String = "Edit your title",
    val description: String = "Edit your description",
    val photos: List<String> = emptyList(),
    val networkPhotos: List<Uri> = emptyList(),
    val attendeeIds: List<String> = emptyList(),
    val attendeeList: List<AttendeeEntity> = emptyList(),
    val deletedPhotoKeys: List<String> = emptyList(),
    val startTime: String = LocalTime.now().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
    val startDate: String = LocalDate.now().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
    val endTime: String = LocalTime.now().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
    val endDate: String = LocalDate.now().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
    val remindAt: ReminderOptions = ReminderOptions.THIRTY_MINUTES_BEFORE,
    val isValidUser: Boolean = false,
    val isGoingToEvent: Boolean = false,
    val isEditingItem: Boolean = false,
    val isEditingDate: Boolean = false,
    val isEditingTime: Boolean = false,
    val isEditingEvent: Boolean = false,
    val isEditingReminder: Boolean = false,
    val isDeletingItem: Boolean = false,
    val isValidatingAttendee: Boolean = false
)

data class EventAttendeeData(
    val id: String = "",
    val fullName: String = "",
    val email: String = ""
)
