package com.example.taskyapplication.agenda.items.task.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.main.data.Task
import com.example.taskyapplication.agenda.items.main.data.asTaskEntity
import com.example.taskyapplication.agenda.items.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.items.task.data.local.entity.DeletedTaskIdEntity
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

interface LocalDataSource {
    suspend fun upsertDeletedTaskId(deletedTaskId: DeletedTaskIdEntity): Result<Unit>
    suspend fun getAllDeletedTaskIds(): List<DeletedTaskIdEntity>
    fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>>
    suspend fun upsertTask(task: TaskEntity): Result<Unit>
    suspend fun upsertAllTasks(tasks: List<Task>): kotlin.Result<Unit>
    suspend fun getTask(taskId: String): TaskEntity?
    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllTasks()
    suspend fun getAllTasks(): List<TaskEntity>
}

class TaskLocalDataSource @Inject constructor(
    private val dao: TaskDao
) : LocalDataSource {

    override suspend fun getAllTasks() = dao.getAllTasks()

    override suspend fun upsertTask(task: TaskEntity): kotlin.Result<Unit> {
        return try {
            dao.upsertTask(task)
            kotlin.Result.success(Unit)
        } catch (e: SQLiteFullException) {
            kotlin.Result.failure(e)
        }
    }

    override suspend fun getTask(taskId: String): TaskEntity? {
        return dao.getTaskById(taskId)
    }

    override suspend fun deleteTask(taskId: String) {
        dao.deleteTaskById(taskId)
    }

    override suspend fun upsertAllTasks(tasks: List<Task>): kotlin.Result<Unit> {
        return try {
            tasks.forEach {
                dao.upsertTask(it.asTaskEntity())
            }
            kotlin.Result.success(Unit)
        } catch (e: SQLiteFullException) {
            kotlin.Result.failure(e)
        }
    }

    override suspend fun upsertDeletedTaskId(deletedTaskId: DeletedTaskIdEntity): Result<Unit> {
        return try {
            dao.upsertDeletedTaskId(deletedTaskId)
            kotlin.Result.success(Unit)
        } catch (e: SQLiteFullException) {
            kotlin.Result.failure(e)
        }
    }

    override suspend fun getAllDeletedTaskIds(): List<DeletedTaskIdEntity> {
        return dao.getDeletedTaskIds()
    }

    override fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>> {
        return dao.getAllTasksForSelectedDate(date)
    }

    override suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }
}
