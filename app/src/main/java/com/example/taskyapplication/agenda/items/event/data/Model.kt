package com.example.taskyapplication.agenda.items.event.data

import android.net.Uri

data class CreateEventRequest(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: String,
    val attendeeIds: List<String>
)

data class UpdateEventRequest(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val attendeeIds: List<String>,
    val deletedPhotoKeys: List<String>,
    val isGoing: Boolean
)

data class Attendee(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)

data class NetworkPhotoRequest(
    val photo: Uri
)
