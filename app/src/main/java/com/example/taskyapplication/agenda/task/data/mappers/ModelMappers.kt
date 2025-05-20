package com.example.taskyapplication.agenda.task.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskyapplication.agenda.domain.toDateTime
import com.example.taskyapplication.agenda.domain.toFormattedDate
import com.example.taskyapplication.agenda.domain.toFormattedTime
import com.example.taskyapplication.agenda.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.task.data.network.models.GetTaskResponse
import com.example.taskyapplication.agenda.task.presentation.TaskUiState

fun GetTaskResponse.asEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    time = time,
    remindAt = remindAt,
    isDone = isDone
)

@RequiresApi(Build.VERSION_CODES.O)
fun TaskEntity.asTask() = TaskUiState(
    id = id,
    title = title,
    description = description,
    time = time.toDateTime().toFormattedTime(),
    date = time.toDateTime().toFormattedDate(),
    remindAt = remindAt,
    isDone = isDone
)
