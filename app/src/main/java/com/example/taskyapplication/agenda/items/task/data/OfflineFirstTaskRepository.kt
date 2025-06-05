package com.example.taskyapplication.agenda.items.task.data

import com.example.taskyapplication.agenda.items.task.data.local.dao.PendingTaskDao
import com.example.taskyapplication.agenda.items.task.data.local.entity.asPendingTaskEntity
import com.example.taskyapplication.agenda.items.task.data.mappers.asTaskDomainModel
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.items.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.items.task.domain.RemoteDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskDto
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfflineFirstTaskRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val pendingTaskDao: PendingTaskDao,
    private val applicationScope: CoroutineScope,
) : TaskRepository {

    override suspend fun createNewTask(request: TaskNetworkModel): EmptyResult<DataError> {
        val localResult = localDataSource.upsertTask(request.asTaskDomainModel())
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val remoteResult = remoteDataSource.postTask(request)
        return when (remoteResult) {
            is Result.Error -> {
                applicationScope.launch {
                    pendingTaskDao.upsertPendingTask(request.asPendingTaskEntity())
                }.join()
                Result.Success(Unit)
            }
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertTask(request.asTaskDomainModel())
                }.await()
            }
        }
    }

    override suspend fun updateTask(request: UpdateTaskBody): EmptyResult<DataError> {
        val localResult = localDataSource.upsertTask(request.asTaskDomainModel())
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val remoteResult = remoteDataSource.updateTask(request)
        return when (remoteResult) {
            is Result.Error -> {
                applicationScope.launch {
                    pendingTaskDao.upsertPendingTask(request.asPendingTaskEntity())
                }.join()
                Result.Success(Unit)
            }
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertTask(request.asTaskDomainModel())
                }.await()
            }
        }
    }

    override suspend fun deleteTask(taskId: String): EmptyResult<DataError> {
        localDataSource.deleteTask(taskId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteTask(taskId)
        }.await()
        if (remoteResult !is Result.Success) {
           TODO("Implement the logic for handling the error case when deleting a task")
//            applicationScope.launch {
//                pendingTaskDao.saveTaskToDelete(taskId)
//            }.join()
        }
        return remoteResult.asEmptyDataResult()
    }

    override suspend fun getTaskById(taskId: String): TaskDto {
       return localDataSource.getTask(taskId)
    }
}
