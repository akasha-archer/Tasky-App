package com.example.taskyapplication.agenda.items.main.data

import com.example.taskyapplication.agenda.items.event.data.EventPhotoResponse
import com.example.taskyapplication.agenda.items.event.data.GetAttendeeResponse
import kotlinx.serialization.Serializable

// Response for fetching agenda for selected date
@Serializable
data class AgendaItemsResponse(
    val events: List<Event>,
    val tasks: List<Task>,
    val reminders: List<Reminder>
)

@Serializable
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val attendees: List<GetAttendeeResponse>,
    val photos: List<EventPhotoResponse>
)

@Serializable
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
)

@Serializable
data class Reminder(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
