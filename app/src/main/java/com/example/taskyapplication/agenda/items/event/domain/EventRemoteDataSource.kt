package com.example.taskyapplication.agenda.items.event.domain

import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.network.EventApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import javax.inject.Inject

interface EventRemoteDataSource {
    suspend fun getReminder(reminderId: String): Result<CreatedEventResponse, DataError.Network>
    suspend fun postReminder(event: CreateEventNetworkModel): EmptyResult<DataError>
    suspend fun updateReminder(event: UpdateEventNetworkModel): EmptyResult<DataError>
    suspend fun deleteReminder(eventId: String): EmptyResult<DataError>
}

class EventRemoteDataSourceImpl @Inject constructor(
eventApi: EventApiService
) : EventRemoteDataSource {
    override suspend fun getReminder(reminderId: String): Result<CreatedEventResponse, DataError.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun postReminder(event: CreateEventNetworkModel): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateReminder(event: UpdateEventNetworkModel): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReminder(eventId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

}
