package com.example.taskyapplication.agenda.reminder.domain.network

import com.example.taskyapplication.agenda.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.reminder.data.models.UpdateReminderBody
import com.example.taskyapplication.agenda.task.data.network.models.GetTaskResponse
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
        @Body request: UpdateReminderBody
    ): Response<Unit>

    @GET("/reminder")
    suspend fun getReminderById(
        @Query("reminderId") reminderId: String
    ): Response<GetTaskResponse>

    @DELETE("/reminder")
    suspend fun deleteReminderById(
        @Query("reminderId") reminderId: String
    ): Response<Unit>
}
