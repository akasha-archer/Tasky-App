package com.example.taskyapplication.agenda.items.task.data.mappers

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.domain.convertDateAndTimeStringsToLong
import com.example.taskyapplication.agenda.domain.toDateTime
import com.example.taskyapplication.agenda.domain.toFormattedDate
import com.example.taskyapplication.agenda.domain.toFormattedTime
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskResponse
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.items.task.presentation.TaskUiState

fun TaskResponse.asTaskEntity(): TaskEntity {
    val timeAsString = time.toDateTime().toFormattedTime()
    val dateAsString = time.toDateTime().toFormattedDate()

    return TaskEntity(
        id = id,
        title = title,
        description = description,
        date = dateAsString,
        time = timeAsString,
        remindAt = remindAt,
        isDone = isDone
    )
}

fun TaskNetworkModel.asTaskEntity(): TaskEntity {
    val timeAsString = startTime.toDateTime().toFormattedTime()
    val dateAsString = startTime.toDateTime().toFormattedDate()

    return TaskEntity(
        id = itemId,
        title = title,
        description = description,
        date = dateAsString,
        time = timeAsString,
        remindAt = reminderTime,
        isDone = isDone
    )
}

fun UpdateTaskBody.asTaskEntity(): TaskEntity {
    val timeAsString = startTime.toDateTime().toFormattedTime()
    val dateAsString = startTime.toDateTime().toFormattedDate()

    return TaskEntity(
        id = itemId,
        title = title,
        description = description,
        date = dateAsString,
        time = timeAsString,
        remindAt = reminderTime,
        isDone = isDone
    )
}

fun TaskResponse.asTaskUi() = TaskUiState(
    id = id,
    title = title,
    description = description,
    time = time.toDateTime().toFormattedTime(),
    date = time.toDateTime().toFormattedDate(),
    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
    isDone = isDone
)

fun TaskEntity.asTaskUi() = TaskUiState(
    id = id,
    title = title,
    description = description,
    time = time,
    date = date,
    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
    isDone = isDone
)

fun TaskUiState.asTaskNetworkModel() = TaskNetworkModel(
    itemId = id,
    title = title,
    description = description,
    startTime = convertDateAndTimeStringsToLong(time, date),
    reminderTime = 0L,
    isDone = isDone
)

fun TaskUiState.asUpdateTaskNetworkModel() = UpdateTaskBody(
    itemId = id,
    title = title,
    description = description,
    startTime = convertDateAndTimeStringsToLong(time, date),
    reminderTime = 0L,
    isDone = isDone
)

//fun UpdateTaskBody.asTaskDomainModel() = TaskDto(
//    id = itemId,
//    title = title,
//    description = description,
//    time = startTime.toDateTime().toFormattedTime(),
//    date = startTime.toDateTime().toFormattedDate(),
//    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
//    //reminderTime,
//    isDone = isDone
//)
//
//fun TaskNetworkModel.asTaskDomainModel() = TaskDto(
//    id = itemId,
//    title = title,
//    description = description,
//    time = startTime.toDateTime().toFormattedTime(),
//    date = startTime.toDateTime().toFormattedDate(),
//    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
//    //reminderTime,
//    isDone = isDone
//)
//
//fun TaskEntity.asTaskDomainModel() = TaskDto(
//    id = id,
//    title = title,
//    description = description,
//    time = time.toDateTime().toFormattedTime(),
//    date = time.toDateTime().toFormattedDate(),
//    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
//    isDone = isDone
//)



//fun TaskDto.asTaskEntity() = TaskEntity(
//    id = id,
//    title = title,
//    description = description,
//    time = convertDateAndTimeStringsToLong(time, date),
//    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE.value.inWholeMilliseconds,
//    isDone = isDone
//)

