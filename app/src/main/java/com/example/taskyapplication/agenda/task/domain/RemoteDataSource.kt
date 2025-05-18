package com.example.taskyapplication.agenda.task.domain

import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.domain.model.GetTaskResponse
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result

interface RemoteDataSource {
    suspend fun getTask(taskId: String): Result<GetTaskResponse, DataError.Network>
    suspend fun postTask(task: TaskNetworkModel): EmptyResult<DataError>
    suspend fun updateTask(task: TaskNetworkModel): EmptyResult<DataError>
    suspend fun deleteTask(taskId: String): EmptyResult<DataError>
}