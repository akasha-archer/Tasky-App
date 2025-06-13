package com.example.taskyapplication.agenda.items.task.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.main.data.Task
import com.example.taskyapplication.agenda.items.main.data.asTaskEntity
import com.example.taskyapplication.agenda.items.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

interface LocalDataSource {
    fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>>
    suspend fun upsertTask(task: TaskEntity): Result<Unit, DataError.Local>
    suspend fun upsertAllTasks(tasks: List<Task>): Result<Unit, DataError.Local>
    suspend fun getTask(taskId: String): TaskEntity
    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllTasks()
    suspend fun getAllTasks(): List<TaskEntity>
}

class TaskLocalDataSource @Inject constructor(
    private val dao: TaskDao
) : LocalDataSource {

    override suspend fun getAllTasks() = dao.getAllTasks()

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

    override suspend fun upsertAllTasks(tasks: List<Task>): Result<Unit, DataError.Local> {
       return try {
           tasks.forEach {
               dao.upsertTask(it.asTaskEntity())
           }
           Result.Success(Unit)
       } catch(e: SQLiteFullException) {
           Result.Error(DataError.Local.DISK_FULL)
       }
    }

    override fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>> {
        return dao.getAllTasksForSelectedDate(date)
    }

    override suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }
}
