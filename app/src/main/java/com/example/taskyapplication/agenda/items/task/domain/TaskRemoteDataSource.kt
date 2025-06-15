package com.example.taskyapplication.agenda.items.task.domain

import com.example.taskyapplication.agenda.items.task.data.network.models.TaskResponse
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.items.task.domain.network.TaskApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.safeApiCall
import retrofit2.Response
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getTask(taskId: String): Result<TaskResponse, DataError.Network>
    suspend fun createTask(task: TaskNetworkModel): kotlin.Result<Unit>
    suspend fun updateTask(task: UpdateTaskBody): EmptyResult<DataError>
    suspend fun deleteTask(taskId: String): EmptyResult<DataError>
}

class TaskRemoteDataSource @Inject constructor(
    private val taskApi: TaskApiService
): RemoteDataSource {

    override suspend fun getTask(taskId: String): Result<TaskResponse, DataError.Network> {
        return safeApiCall {
            taskApi.getTaskById(taskId)
        }
    }

    override suspend fun createTask(task: TaskNetworkModel): kotlin.Result<Unit> {
         return taskApi.createNewTask(task)


    //        return safeApiCall {
//            taskApi.createNewTask(task)
//        }
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
