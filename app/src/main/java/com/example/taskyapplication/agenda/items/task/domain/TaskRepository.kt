package com.example.taskyapplication.agenda.items.task.domain

import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult

interface TaskRepository {
    suspend fun createNewTask(
        request: TaskNetworkModel
    ): EmptyResult<DataError>

    suspend fun updateTask(
        request: UpdateTaskBody
    ): EmptyResult<DataError>

    suspend fun getTaskById(
        taskId: String
    ): TaskEntity

    suspend fun deleteTask(
        taskId: String
    ): EmptyResult<DataError>
}