package com.example.taskyapplication.agenda.items.task.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskyapplication.agenda.items.task.data.network.models.GetTaskResponse
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.items.task.domain.network.TaskApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.safeApiCall
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getTask(taskId: String): Result<GetTaskResponse, DataError.Network>
    suspend fun postTask(task: TaskNetworkModel): EmptyResult<DataError>
    suspend fun updateTask(task: UpdateTaskBody): EmptyResult<DataError>
    suspend fun deleteTask(taskId: String): EmptyResult<DataError>
}

class TaskRemoteDataSource @Inject constructor(
    private val taskApi: TaskApiService
): RemoteDataSource {

//    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTask(taskId: String): Result<GetTaskResponse, DataError.Network> {
        return safeApiCall {
            taskApi.getTaskById(taskId)
        }
    }

    override suspend fun postTask(task: TaskNetworkModel): EmptyResult<DataError> {
        return safeApiCall {
            taskApi.createNewTask(task)
        }
    }

    override suspend fun updateTask(task: UpdateTaskBody): EmptyResult<DataError> {
        return safeApiCall {
            taskApi.updateTask(task)
        }
    }

    override suspend fun deleteTask(taskId: String): EmptyResult<DataError> {
        return safeApiCall {
            taskApi.deleteTaskById(taskId)
        }
    }
}
