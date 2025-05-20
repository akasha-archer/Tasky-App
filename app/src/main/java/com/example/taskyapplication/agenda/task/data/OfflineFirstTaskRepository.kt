package com.example.taskyapplication.agenda.task.data

import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.task.domain.RemoteDataSource
import com.example.taskyapplication.agenda.task.domain.TaskLocalDataSource
import com.example.taskyapplication.agenda.task.domain.TaskRemoteDataSource
import com.example.taskyapplication.agenda.task.domain.TaskRepository
import com.example.taskyapplication.agenda.task.domain.network.TaskApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import javax.inject.Inject

class OfflineFirstTaskRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    taskApi: TaskApiService,
): TaskRepository {

    override suspend fun createNewTask(request: TaskNetworkModel): EmptyResult<DataError> {
       // when remote.createTask is error
        // return emptyDataResult

        // when success -> local.upsert
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(request: UpdateTaskBody): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }
}