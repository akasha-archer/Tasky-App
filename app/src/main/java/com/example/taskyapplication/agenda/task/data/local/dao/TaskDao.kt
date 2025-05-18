package com.example.taskyapplication.agenda.task.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taskyapplication.agenda.task.data.local.entities.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: String): TaskEntity // to identify task being updated

    @Query("DELETE FROM tasks WHERE id = :taskId")
    fun deleteTaskById(taskId: String)

    @Insert
    fun insertTask(taskEntity: TaskEntity)

    @Insert
    fun insertAllTasks(tasks: List<TaskEntity>)

    @Delete
    fun deleteAllTasks(tasks: List<TaskEntity>) // to delete all tasks when user logs out
}