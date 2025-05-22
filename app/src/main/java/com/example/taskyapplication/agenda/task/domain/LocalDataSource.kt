package com.example.taskyapplication.agenda.task.domain

import android.database.sqlite.SQLiteFullException
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskyapplication.agenda.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.task.data.local.entity.TaskEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun getTasks(): Flow<List<TaskEntity>>
    suspend fun upsertTask(task: TaskEntity): Result<Unit, DataError.Local>
    suspend fun upsertAllTasks(tasks: List<TaskEntity>): Result<Unit, DataError.Local>
    suspend fun getTask(taskId: String): TaskEntity
    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllTasks()
}

class TaskLocalDataSource @Inject constructor(
    private val dao: TaskDao
) : LocalDataSource {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun upsertTask(task: TaskEntity): Result<Unit, DataError.Local> {
        return try {
            dao.upsertTask(task)
            Result.Success(Unit)
        } catch(e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getTask(taskId: String): TaskEntity {
        return dao.getTaskById(taskId)
    }

    override suspend fun deleteTask(taskId: String) {
        dao.deleteTaskById(taskId)
    }

    override suspend fun upsertAllTasks(tasks: List<TaskEntity>): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    override fun getTasks(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }
}
