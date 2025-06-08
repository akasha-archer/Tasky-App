package com.example.taskyapplication.agenda.items.event.domain

import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.EventDto
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import javax.inject.Inject

class EventOfflineFirstRepository @Inject constructor(
    private val eventLocalDataSource: EventLocalDataSource,
    private val eventRemoteDataSource: EventRemoteDataSource
): EventRepository {

    override suspend fun createNewEvent(createEventNetworkModel: CreateEventNetworkModel): Result<CreatedEventResponse, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateEvent(updateEventNetworkModel: UpdateEventNetworkModel): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getEventById(eventId: String): EventDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvent(eventId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

}