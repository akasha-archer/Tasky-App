package com.example.taskyapplication.agenda.items.event.network

import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.GetAttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.FetchedEventResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.UpdatedEventResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface EventApiService {

    @Multipart
    @POST("event")
    suspend fun createEvent(
        @Part("create_event_request") createEventRequest: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Response<CreatedEventResponse>

    @GET("event/{eventId}")
    suspend fun getEventById(
        @Query("eventId") eventId: String
    ): Response<FetchedEventResponse>

    @Multipart
    @PUT("event")
    suspend fun updateEvent(
        @Part("update_event_request") updateEventRequest: UpdateEventNetworkModel,
        @Part images: List<MultipartBody.Part>
    ): Response<UpdatedEventResponse>

    @DELETE("event/{eventId}")
    suspend fun deleteEvent(
        @Query("eventId") eventId: String
    ): Response<Unit>

    @GET("attendee/{email}")
    suspend fun verifyAttendee(
        @Query("email") attendeeEmail: String
    ): Response<GetAttendeeResponse>

    @DELETE("attendee/{eventId}")
    suspend fun deleteAttendee(
        @Query("eventId") eventId: String,
    ): Response<Unit>
}
