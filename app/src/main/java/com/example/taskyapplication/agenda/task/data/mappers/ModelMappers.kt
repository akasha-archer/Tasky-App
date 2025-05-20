package com.example.taskyapplication.agenda.task.data.mappers

import com.example.taskyapplication.agenda.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.presentation.TaskUiState

fun TaskNetworkModel.asEntity() = TaskEntity(
    id = eventId,
    title = title,
    description = description,
    time = startTime,
    remindAt = reminderTime,
    isDone = isDone
)

fun TaskEntity.asTask() = TaskUiState(
    id = id,
    title = title,
    description = description,
    time = time,
    remindAt = remindAt,
    isDone = isDone
)