package com.example.taskyapplication.agenda.task.data.local

import com.example.taskyapplication.agenda.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.task.presentation.TaskUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomLocalDataSource @Inject constructor(
    private val taskDao: TaskDao
): LocalDataSource {

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