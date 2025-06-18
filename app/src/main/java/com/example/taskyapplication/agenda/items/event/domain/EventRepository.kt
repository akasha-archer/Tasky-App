package com.example.taskyapplication.agenda.items.event.domain

import android.net.Uri
import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.GetAttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import okhttp3.MultipartBody

interface EventRepository {
    suspend fun createMultiPartImages(userPhots: List<Uri>): List<MultipartBody.Part>

    suspend fun validateAttendee(email: String): Result<GetAttendeeResponse>
    suspend fun getAttendeeListForEvent(eventId: String): List<AttendeeEntity>
    suspend fun createNewEvent(
        request: CreateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<Unit>

    suspend fun updateEvent(
        request: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<Unit>

    suspend fun getEventWithoutImages(
        eventId: String
    ): EventEntity?

    suspend fun deleteEvent(
        eventId: String
    ): Result<Unit>
}
