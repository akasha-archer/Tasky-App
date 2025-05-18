package com.example.taskyapplication.agenda.task.data

import com.example.taskyapplication.agenda.task.domain.TaskRequestBody
import com.example.taskyapplication.agenda.task.domain.UpdateTaskBody
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult

interface TaskRepository {
    suspend fun createNewTask(
        request: TaskRequestBody
    ): EmptyResult<DataError>

    suspend fun updateTask(
        request: UpdateTaskBody
    ): EmptyResult<DataError>

    suspend fun getTaskById(
        taskId: String
    ): EmptyResult<DataError>

    suspend fun deleteTask(
        taskId: String
    ): EmptyResult<DataError>
}