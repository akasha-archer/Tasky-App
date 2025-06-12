package com.example.taskyapplication.agenda.items.task.data.mappers

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.domain.asLocalDateValue
import com.example.taskyapplication.agenda.domain.asLocalTimeValue
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskResponse
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.items.task.presentation.TaskUiState
import java.time.LocalDateTime
import java.time.ZoneId

fun TaskResponse.asTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        date = time.asLocalDateValue(),
        time = time.asLocalTimeValue(),
        remindAt = remindAt,
        isDone = isDone
    )
}

fun TaskNetworkModel.asTaskEntity(): TaskEntity {
    return TaskEntity(
        id = itemId,
        title = title,
        description = description,
        date = startTime.asLocalDateValue(),
        time = startTime.asLocalTimeValue(),
        remindAt = reminderTime,
        isDone = isDone
    )
}

fun UpdateTaskBody.asTaskEntity(): TaskEntity {
    return TaskEntity(
        id = itemId,
        title = title,
        description = description,
        date = startTime.asLocalDateValue(),
        time = startTime.asLocalTimeValue(),
        remindAt = reminderTime,
        isDone = isDone
    )
}

fun TaskResponse.asTaskUi(): TaskUiState {
    return TaskUiState(
        id = id,
        title = title,
        description = description,
        time = time.asLocalTimeValue(),
        date = time.asLocalDateValue(),
        remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
        isDone = isDone
    )
}

fun TaskEntity.asTaskUi() = TaskUiState(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
    isDone = isDone
)

fun TaskUiState.asTaskNetworkModel(): TaskNetworkModel {
    val combinedStartTime = LocalDateTime.of(date, time)
    val startTimeAsLong = combinedStartTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val reminderTimeMillis = startTimeAsLong - remindAt.asLong

    return TaskNetworkModel(
        itemId = id,
        title = title,
        description = description,
        startTime = startTimeAsLong,
        reminderTime = reminderTimeMillis,
        isDone = isDone
    )
}

fun TaskUiState.asUpdateTaskNetworkModel(): UpdateTaskBody {
    val combinedStartTime = LocalDateTime.of(date, time)
    val startTimeAsLong = combinedStartTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val reminderTimeMillis = startTimeAsLong - remindAt.asLong
    return UpdateTaskBody(
        itemId = id,
        title = title,
        description = description,
        startTime = startTimeAsLong,
        reminderTime = reminderTimeMillis,
        isDone = isDone
    )
}
