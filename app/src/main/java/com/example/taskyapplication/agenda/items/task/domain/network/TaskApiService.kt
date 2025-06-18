package com.example.taskyapplication.agenda.items.task.domain.network

import com.example.taskyapplication.agenda.items.task.data.network.models.TaskResponse
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TaskApiService {
    @POST("task")
    suspend fun createNewTask(
        @Body request: TaskNetworkModel
    ): Response<Unit>

    @PUT("task")
    suspend fun updateTask(
        @Body request: UpdateTaskBody
    ): Response<Unit>

    @GET("task/{taskId}")
    suspend fun getTaskById(
        @Query("taskId") taskId: String
    ): Response<TaskResponse>

    @DELETE("task")
    suspend fun deleteTaskById(
        @Query("taskId") taskId: String
    ): Response<Unit>
}
