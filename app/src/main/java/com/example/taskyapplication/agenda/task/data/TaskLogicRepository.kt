package com.example.taskyapplication.agenda.task.data

import com.example.taskyapplication.agenda.task.domain.network.TaskApi
import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.task.domain.TaskRepository
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import javax.inject.Inject

class TaskLogicRepository @Inject constructor(
    taskApi: TaskApi,
): TaskRepository {
    override suspend fun createNewTask(request: TaskNetworkModel): EmptyResult<DataError> {
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