package com.example.taskyapplication.agenda.task.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.taskyapplication.agenda.task.data.local.entity.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: String): TaskEntity

    @Query("DELETE FROM tasks WHERE id = :taskId")
    fun deleteTaskById(taskId: String)

    @Upsert
    fun upsertTask(taskEntity: TaskEntity)

    @Upsert
    fun upsertAllTasks(tasks: List<TaskEntity>)

    @Delete
    fun deleteAllTasks(tasks: List<TaskEntity>) // to delete all tasks when user logs out
}