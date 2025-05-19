package com.example.taskyapplication.agenda.task.domain

import com.example.taskyapplication.agenda.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.task.domain.model.TaskUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun getTasks(): Flow<List<TaskUiState>>
    suspend fun upsertTask(task: TaskUiState)
    suspend fun upsertAllTasks(tasks: List<TaskUiState>)
    suspend fun getTask(taskId: String): TaskUiState
    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllTasks()
}

class TaskLocalDataSource @Inject constructor(
    private val dao: TaskDao
) : LocalDataSource {
    override fun getTasks(): Flow<List<TaskUiState>> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertTask(task: TaskUiState) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertAllTasks(tasks: List<TaskUiState>) {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String): TaskUiState {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

}