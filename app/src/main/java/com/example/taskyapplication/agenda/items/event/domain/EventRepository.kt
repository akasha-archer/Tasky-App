package com.example.taskyapplication.agenda.items.event.domain

import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.UpdatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import okhttp3.MultipartBody

interface EventRepository {
    suspend fun createNewEvent(
        createEventNetworkModel: CreateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<CreatedEventResponse, DataError>

    suspend fun updateEvent(
        updateEventNetworkModel: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<UpdatedEventResponse, DataError>

    suspend fun getEventById(
        eventId: String
    ): EventEntity

    suspend fun deleteEvent(
        eventId: String
    ): EmptyResult<DataError>
}
