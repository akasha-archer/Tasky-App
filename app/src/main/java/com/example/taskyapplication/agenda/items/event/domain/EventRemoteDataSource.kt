package com.example.taskyapplication.agenda.items.event.domain

import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.FetchedEventResponse
import com.example.taskyapplication.agenda.items.event.data.GetAttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.UpdatedEventResponse
import com.example.taskyapplication.agenda.items.event.network.EventApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.safeApiCall
import okhttp3.MultipartBody
import javax.inject.Inject

interface EventRemoteDataSource {
    suspend fun getEvent(eventId: String): Result<FetchedEventResponse, DataError.Network>
    suspend fun createEvent(
        event: CreateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<CreatedEventResponse, DataError.Network>
    suspend fun updateEvent(
        event: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<UpdatedEventResponse, DataError.Network>
    suspend fun deleteEvent(eventId: String): EmptyResult<DataError>
    suspend fun verifyAttendee(email: String): Result<GetAttendeeResponse, DataError.Network>
}

class EventRemoteDataSourceImpl @Inject constructor(
    private val eventApi: EventApiService
) : EventRemoteDataSource {

    override suspend fun getEvent(eventId: String): Result<FetchedEventResponse, DataError.Network> {
        return safeApiCall {
            eventApi.getEventById(eventId)
        }
    }

    override suspend fun createEvent(
        event: CreateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<CreatedEventResponse, DataError.Network> {
        return safeApiCall {
            eventApi.createEvent(event, photos)
        }
    }

    override suspend fun updateEvent(
        event: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<UpdatedEventResponse, DataError.Network> {
        return safeApiCall {
            eventApi.updateEvent(event, photos)
        }
    }

    override suspend fun deleteEvent(eventId: String): EmptyResult<DataError> {
        return safeApiCall {
            eventApi.deleteEvent(eventId)
        }
    }

    override suspend fun verifyAttendee(email: String): Result<GetAttendeeResponse, DataError.Network> {
        return safeApiCall { eventApi.verifyAttendee(email) }
    }
}
