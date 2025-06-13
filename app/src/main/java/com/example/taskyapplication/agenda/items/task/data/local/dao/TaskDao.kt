package com.example.taskyapplication.agenda.items.task.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE date = :date ORDER BY time ASC")
    fun getAllTasksForSelectedDate(date: LocalDate): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TaskEntity

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: String)

    @Upsert
    suspend fun upsertTask(taskEntity: TaskEntity)

    @Upsert
    suspend fun upsertAllTasks(tasks: List<TaskEntity>)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks() // to delete all tasks when user logs out
}