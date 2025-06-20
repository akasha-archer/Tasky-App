package com.example.taskyapplication.agenda.items.task.data

import android.util.Log
import com.example.taskyapplication.agenda.items.task.data.local.entity.DeletedTaskIdEntity
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.items.task.data.mappers.asTaskEntity
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.items.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.items.task.domain.RemoteDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.domain.utils.SUCCESS_CODE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

class OfflineFirstTaskRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val applicationScope: CoroutineScope,
) : TaskRepository {

    override suspend fun createNewTask(request: TaskNetworkModel): Result<Unit> {
        val localResult = localDataSource.upsertTask(request.asTaskEntity())
        if (localResult != kotlin.Result.success(Unit)) {
            Log.e("Task Repository: Error inserting new task", "error: $localResult")
        }
        Log.i("Task Repository: Finished posting to database", "local storage success")
        return try {
            val remoteResult = remoteDataSource.createTask(request)
            if (remoteResult.code() == SUCCESS_CODE) {
                Log.i("Task Repository:", "Task created successfully")
            } else {
                Log.e(
                    "Task Repository: Error creating ${request.title} task",
                    remoteResult.message()
                )
            }
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Task Repo:", e.message.toString())
            kotlin.Result.failure(e)
        }
    }

    override suspend fun updateTask(request: UpdateTaskBody): kotlin.Result<Unit> {
        val localResult = localDataSource.upsertTask(request.asTaskEntity())
        if (localResult != kotlin.Result.success(Unit)) {
            Log.e("Task Repository: Error updating task", "error: $localResult")
        }
        return try {
            val remoteResult = remoteDataSource.updateTask(request)
            if (remoteResult.code() == SUCCESS_CODE) {
                Log.i("Task Repository:", "Task updated successfully")
            } else {
                Log.e(
                    "Task Repository: Error updating ${request.title} task",
                    remoteResult.toString()
                )
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Task Repo:", e.message.toString())
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(taskId: String): kotlin.Result<Unit> {
       localDataSource.deleteTask(taskId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteTask(taskId)
        }.await()
        if (remoteResult.code() != SUCCESS_CODE) {
            localDataSource.upsertDeletedTaskId(DeletedTaskIdEntity(taskId))
            Log.e("Task Repository", "error deleting task: $remoteResult")
        }
        return Result.success(Unit)
    }

    override suspend fun getTaskById(taskId: String): TaskEntity? {
        return localDataSource.getTask(taskId)
    }
}
