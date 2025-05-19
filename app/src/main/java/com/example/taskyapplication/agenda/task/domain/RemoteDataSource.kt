package com.example.taskyapplication.agenda.task.domain

import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.domain.model.GetTaskResponse
import com.example.taskyapplication.agenda.task.domain.network.TaskApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getTask(taskId: String): Result<GetTaskResponse, DataError.Network>
    suspend fun postTask(task: TaskNetworkModel): EmptyResult<DataError>
    suspend fun updateTask(task: TaskNetworkModel): EmptyResult<DataError>
    suspend fun deleteTask(taskId: String): EmptyResult<DataError>
}

class TaskRemoteDataSource @Inject constructor(
    private val taskApi: TaskApiService
): RemoteDataSource {
    override suspend fun getTask(taskId: String): Result<GetTaskResponse, DataError.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun postTask(task: TaskNetworkModel): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: TaskNetworkModel): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }
}