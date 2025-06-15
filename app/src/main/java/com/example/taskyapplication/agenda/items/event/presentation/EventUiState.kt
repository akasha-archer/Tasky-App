package com.example.taskyapplication.agenda.items.event.presentation

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import java.time.LocalDate
import java.time.LocalTime

data class EventUiState(
    val id: String = "",
    val title: String = "Edit your title",
    val description: String = "Edit your description",
    val photos: List<String> = emptyList(),
    val attendeeIds: List<String> = emptyList(),
    val attendeeNames: List<String> = emptyList(),
    val attendeeList: List<AttendeeEntity> = emptyList(),
    val deletedPhotoKeys: List<String> = emptyList(),
    val startTime: LocalTime = LocalTime.now(),
    val startDate: LocalDate = LocalDate.now(),
    val endTime: LocalTime = LocalTime.now(),
    val endDate: LocalDate = LocalDate.now(),
    val remindAt: ReminderOptions = ReminderOptions.THIRTY_MINUTES_BEFORE,
    val isValidUser: Boolean = false,
    val isGoingToEvent: Boolean = false,
    val isEditingItem: Boolean = false,
    val isEditingDate: Boolean = false,
    val isEditingTime: Boolean = false,
    val isEditingEvent: Boolean = false,
    val isEditingReminder: Boolean = false,
    val isDeletingItem: Boolean = false,
    val isValidatingAttendee: Boolean = false,
    val isUserOnline: Boolean = false
)
