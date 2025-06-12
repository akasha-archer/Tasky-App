package com.example.taskyapplication.agenda.items.event.data

import kotlinx.serialization.Serializable

@Serializable
data class CreatedEventResponse(
    val id: String,
    val title: String,
    val description: String?,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val attendees: List<AttendeeResponse>,
    val photos: List<EventPhotoResponse>
)

@Serializable
data class UpdatedEventResponse(
    val id: String,
    val title: String,
    val description: String?,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean,
    val attendees: List<AttendeeResponse>,
    val photos: List<EventPhotoResponse>
)

@Serializable
data class FetchedEventResponse(
    val id: String,
    val title: String,
    val description: String?,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean,
    val attendees: List<AttendeeResponse>,
    val photos: List<EventPhotoResponse>
)

//@Serializable
//data class FetchedEventAttendeeResponse(
//    val email: String,
//    val fullName: String,
//    val userId: String,
//    val eventId: String,
//    val isGoing: Boolean,
//    val remindAt: Long
//)

//@Serializable
//data class UpdatedEventAttendeeResponse(
//    val email: String,
//    val fullName: String,
//    val userId: String,
//    val eventId: String,
//    val isGoing: Boolean,
//    val remindAt: Long
//)


@Serializable
data class AttendeeResponse(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)

@Serializable
data class GetAttendeeResponse(
    val doesUserExist: Boolean,
    val attendee: VerifyAttendeeResponse
)

@Serializable
data class VerifyAttendeeResponse(
    val email: String,
    val fullName: String,
    val userId: String
)

@Serializable
data class EventPhotoResponse(
    val key: String,
    val url: String
)