package com.example.taskyapplication.agenda.items.event.network

import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.FetchAttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.FetchedEventResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import okhttp3.MultipartBody
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
    @POST("/event")
    suspend fun createEvent(
        @Part("create_event_request") createEventRequest: CreateEventNetworkModel,
        @Part files: List<MultipartBody.Part?>
    ): Response<CreatedEventResponse>

    @GET("event/{eventId}")
    suspend fun getEventById(
        @Query("eventId") eventId: String
    ): Response<FetchedEventResponse>

    @Multipart
    @PUT("event")
    suspend fun updateEvent(
        @Part("update_event_request") updateEventRequest: UpdateEventNetworkModel,
        @Part files: List<MultipartBody.Part?>
    ): Response<Unit>

    @DELETE("event/{eventId}")
    suspend fun deleteEvent(
        @Query("eventId") eventId: String
    ): Response<Unit>

    @GET("/attendee/{email}")
    suspend fun getAttendee(
        @Query("email") attendeeEmail: String
    ): Response<FetchAttendeeResponse>

    @DELETE("/attendee/{eventId}")
    suspend fun deleteAttendee(
        @Query("eventId") eventId: String,
    ): Response<Unit>
}
