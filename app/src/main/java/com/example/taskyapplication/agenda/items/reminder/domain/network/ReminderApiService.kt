package com.example.taskyapplication.agenda.items.reminder.domain.network

import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderResponse
import com.example.taskyapplication.agenda.items.reminder.data.models.UpdateReminderNetworkModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ReminderApiService {
    @POST("/reminder")
    suspend fun createNewReminder(
        @Body request: ReminderNetworkModel
    ): Response<Unit>

    @PUT("/reminder")
    suspend fun updateReminder(
        @Body request: UpdateReminderNetworkModel
    ): Response<Unit>

    @GET("/reminder/{reminderId}")
    suspend fun getReminderById(
        @Query("reminderId") reminderId: String
    ): Response<ReminderResponse>

    @DELETE("/reminder/{reminderId}")
    suspend fun deleteReminderById(
        @Query("reminderId") reminderId: String
    ): Response<Unit>
}
