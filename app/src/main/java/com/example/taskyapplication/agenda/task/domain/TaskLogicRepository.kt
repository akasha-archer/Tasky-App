package com.example.taskyapplication.agenda.task.domain

import com.example.taskyapplication.agenda.task.data.TaskRepository
import com.example.taskyapplication.agenda.task.domain.network.TaskApi
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import javax.inject.Inject

class TaskLogicRepository @Inject constructor(
    taskApi: TaskApi,
): TaskRepository {
    override suspend fun createNewTask(request: TaskRequestBody): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(request: UpdateTaskBody): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }
}