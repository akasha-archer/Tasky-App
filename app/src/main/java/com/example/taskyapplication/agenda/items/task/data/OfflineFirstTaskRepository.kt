package com.example.taskyapplication.agenda.items.task.data

import android.util.Log
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.items.task.data.mappers.asTaskEntity
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.items.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.items.task.domain.RemoteDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.agenda.items.task.domain.network.TaskApiService
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
    private val taskApiService: TaskApiService
) : TaskRepository {

    override suspend fun createNewTask(request: TaskNetworkModel): kotlin.Result<Unit> {
        val localResult = localDataSource.upsertTask(request.asTaskEntity())
        if (localResult !is Result.Success) {
            Log.e("Task Repository: Error inserting new task", "error: $localResult")
        }
        Log.i("Task Repository: Finished posting to database", "success")
        return try {
            val remoteResult = taskApiService.createNewTask(request)
            if (remoteResult.isSuccess) {
                Log.i("Task Repository:", "Task created successfully")
            } else {
                Log.e(
                    "Task Repository: Error creating ${request.title} task",
                    remoteResult.toString()
                )
            }
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Task Repo:", e.message.toString())
            kotlin.Result.failure(e)
        }
    }

    override suspend fun updateTask(request: UpdateTaskBody): EmptyResult<DataError> {
        val localResult = localDataSource.upsertTask(request.asTaskEntity())
        if (localResult !is Result.Success) {
            Log.e("Task Repository: Error updating task", "error: $localResult")
            return localResult.asEmptyDataResult()
        }

        val remoteResult = remoteDataSource.updateTask(request)
        return when (remoteResult) {
            is Result.Error -> {
                Log.e("Task Repository: Error updating task", remoteResult.error.toString())
                Result.Error(remoteResult.error)
            }

            is Result.Success -> {
                Log.e("Task Repository:", "Task updated successfully")
                Result.Success(Unit)
            }
        }
    }

    override suspend fun deleteTask(taskId: String): EmptyResult<DataError> {
        localDataSource.deleteTask(taskId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteTask(taskId)
        }.await()
        if (remoteResult !is Result.Success) {
            Log.e("Task Repository", "error deleting task: $remoteResult")
        }
        return remoteResult.asEmptyDataResult()
    }

    override suspend fun getTaskById(taskId: String): TaskEntity? {
        return localDataSource.getTask(taskId)
    }
}
