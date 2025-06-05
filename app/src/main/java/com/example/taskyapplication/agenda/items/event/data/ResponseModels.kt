package com.example.taskyapplication.agenda.items.event.data

data class CreatedEventResponse(
    val id: String,
    val title: String,
    val description: String?,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val attendees: List<Attendee>,
    val photos: List<EventPhoto>
)

data class UpdatedEventResponse(
    val id: String,
    val title: String,
    val description: String?,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean,
    val attendees: List<Attendee>,
    val photos: List<EventPhoto>
)

data class FetchedEventResponse(
    val id: String,
    val title: String,
    val description: String?,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean,
    val attendees: List<Attendee>,
    val photos: List<EventPhoto>
)

data class UpdatedEventAttendeeResponse(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)

data class UpdatedEventPhotoResponse(
    val key: String,
    val url: String,
)

data class FetchedAttendeeResponse(
    val doesUserExist: Boolean,
    val attendee: FetchedAttendeeDetailsResponse
)

data class FetchedAttendeeDetailsResponse(
    val email: String,
    val fullName: String,
    val userId: String
)