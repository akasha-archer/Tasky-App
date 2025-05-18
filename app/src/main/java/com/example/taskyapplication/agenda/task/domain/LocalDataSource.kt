package com.example.taskyapplication.agenda.task.domain

import com.example.taskyapplication.agenda.task.domain.model.TaskUiState
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getTasks(): Flow<List<TaskUiState>>
    suspend fun upsertTask(task: TaskUiState)
    suspend fun upsertAllTasks(tasks: List<TaskUiState>)
    suspend fun getTask(taskId: String): TaskUiState
    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllTasks()
}