package com.example.taskyapplication.agenda.items.event.domain

import android.content.Context
import android.net.Uri
import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.GetAttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.UpdatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import okhttp3.MultipartBody

interface EventRepository {

    suspend fun createMultiPartImages(userPhots: List<Uri>): List<MultipartBody.Part>

    suspend fun validateAttendee(email: String):  Result<GetAttendeeResponse, DataError>
    suspend fun getAttendeeListForEvent(eventId: String): List<AttendeeEntity>
    suspend fun createNewEvent(
        createEventNetworkModel: CreateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<CreatedEventResponse, DataError>

    suspend fun updateEvent(
        updateEventNetworkModel: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<UpdatedEventResponse, DataError>

    suspend fun getEventWithoutImages(
        eventId: String
    ): EventEntity

    suspend fun deleteEvent(
        eventId: String
    ): EmptyResult<DataError>
}
