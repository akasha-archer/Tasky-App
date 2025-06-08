package com.example.taskyapplication.agenda.items.event.domain

import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.EventDto
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result

interface EventRepository {
    suspend fun createNewEvent(
        createEventNetworkModel: CreateEventNetworkModel
    ): Result<CreatedEventResponse, DataError>

    suspend fun updateEvent(
        updateEventNetworkModel: UpdateEventNetworkModel
    ): EmptyResult<DataError>

    suspend fun getEventById(
        eventId: String
    ): EventDto

    suspend fun deleteEvent(
        eventId: String
    ): EmptyResult<DataError>




}

