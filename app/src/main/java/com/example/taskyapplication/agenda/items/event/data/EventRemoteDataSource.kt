package com.example.taskyapplication.agenda.items.event.data

import com.example.taskyapplication.agenda.items.event.network.EventApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

interface EventRemoteDataSource {
    suspend fun getEvent(eventId: String): Response<FetchedEventResponse>
    suspend fun createEvent(
        event: RequestBody,
        photos: List<MultipartBody.Part>
    ): Response<CreatedEventResponse>

    suspend fun updateEvent(
        event: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Response<UpdatedEventResponse>

    suspend fun deleteEvent(eventId: String): Response<Unit>
    suspend fun verifyAttendee(email: String): Response<GetAttendeeResponse>
}

class EventRemoteDataSourceImpl @Inject constructor(
    private val eventApi: EventApiService
) : EventRemoteDataSource {

    override suspend fun getEvent(eventId: String): Response<FetchedEventResponse> {
        return eventApi.getEventById(eventId)
    }

    override suspend fun createEvent(
        event: RequestBody,
        photos: List<MultipartBody.Part>
    ): Response<CreatedEventResponse> {
        return eventApi.createEvent(event, photos)
    }

    override suspend fun updateEvent(
        event: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Response<UpdatedEventResponse> {
        return eventApi.updateEvent(event, photos)
    }

    override suspend fun deleteEvent(eventId: String): Response<Unit> {
        return eventApi.deleteEvent(eventId)
    }

    override suspend fun verifyAttendee(email: String): Response<GetAttendeeResponse> {
        return eventApi.verifyAttendee(email)
    }
}
