package com.example.taskyapplication.agenda.task.domain.network

import com.example.taskyapplication.agenda.task.domain.GetTaskResponse
import com.example.taskyapplication.agenda.task.domain.TaskRequestBody
import com.example.taskyapplication.agenda.task.domain.UpdateTaskBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TaskApi {
    @POST("/task")
    suspend fun createNewTask(
        @Body request: TaskRequestBody
    ): Response<Unit>

    @PUT("/task")
    suspend fun updateTask(
        @Body request: UpdateTaskBody
    ): Response<Unit>

    @GET("/task")
    suspend fun getTaskById(
       @Query("taskId") taskId: String
    ): Response<GetTaskResponse>

    @DELETE("/task")
    suspend fun deleteTaskById(
        @Query("taskId") taskId: String
    ): Response<Unit>
}
