package com.example.taskyapplication.agenda.task.data

import com.example.taskyapplication.agenda.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.task.data.mappers.asEntity
import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.task.domain.RemoteDataSource
import com.example.taskyapplication.agenda.task.domain.TaskRepository
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

class OfflineFirstTaskRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val applicationScope: CoroutineScope,
) : TaskRepository {

    override suspend fun createNewTask(request: TaskNetworkModel): EmptyResult<DataError> {
        val localResult = localDataSource.upsertTask(request.asEntity())
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val remoteResult = remoteDataSource.postTask(request)
        return when (remoteResult) {
            is Result.Error -> {
                TODO("sync logic")
            }
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertTask(request.asEntity())
                }.await()
            }
        }
    }

    override suspend fun updateTask(request: UpdateTaskBody): EmptyResult<DataError> {
        val localResult = localDataSource.upsertTask(request.asEntity())
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val remoteResult = remoteDataSource.updateTask(request)
        return when (remoteResult) {
            is Result.Error -> {
                TODO("sync logic")
            }
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertTask(request.asEntity())
                }.await()
            }
        }
    }

    override suspend fun deleteTask(taskId: String): EmptyResult<DataError> {
        localDataSource.deleteTask(taskId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteTask(taskId)
        }.await()
        TODO("Create sync logic")
    }

    override suspend fun getTaskById(taskId: String): TaskEntity {
       return localDataSource.getTask(taskId)
    }

}