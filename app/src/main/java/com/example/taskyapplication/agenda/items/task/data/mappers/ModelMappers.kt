package com.example.taskyapplication.agenda.items.task.data.mappers

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.items.task.data.network.models.GetTaskResponse
import com.example.taskyapplication.agenda.items.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.items.task.domain.TaskDto
import com.example.taskyapplication.agenda.items.task.presentation.TaskUiState
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun GetTaskResponse.asTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    time = time,
    remindAt = remindAt,
    isDone = isDone
)

fun GetTaskResponse.asTaskUi() = TaskUiState(
    id = id,
    title = title,
    description = description,
    time = time.toDateTime().toFormattedTime(),
    date = time.toDateTime().toFormattedDate(),
    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
    isDone = isDone
)

fun UpdateTaskBody.asTaskEntity() = TaskEntity(
    id = itemId,
    title = title,
    description = description,
    time = startTime,
    remindAt = 0L,
    //reminderTime,
    isDone = isDone
)

fun UpdateTaskBody.asTaskDomainModel() = TaskDto(
    id = itemId,
    title = title,
    description = description,
    time = startTime.toDateTime().toFormattedTime(),
    date = startTime.toDateTime().toFormattedDate(),
    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
    //reminderTime,  // TODO("convert Long to enum value")
    isDone = isDone
)

fun TaskNetworkModel.asTaskDomainModel() = TaskDto(
    id = itemId,
    title = title,
    description = description,
    time = startTime.toDateTime().toFormattedTime(),
    date = startTime.toDateTime().toFormattedDate(),
    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
    //reminderTime, // TODO("convert Long to enum value")
    isDone = isDone
)

fun TaskNetworkModel.asTaskEntity() = TaskEntity(
    id = itemId,
    title = title,
    description = description,
    time = startTime,
    remindAt = reminderTime,
    isDone = isDone
)

fun TaskEntity.asTaskDomainModel() = TaskDto(
    id = id,
    title = title,
    description = description,
    time = time.toDateTime().toFormattedTime(),
    date = time.toDateTime().toFormattedDate(),
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

fun TaskUiState.asUpdateTaskModel() = UpdateTaskBody(
    itemId = id,
    title = title,
    description = description,
    startTime = convertDateAndTimeStringsToLong(time, date),
    reminderTime = 0L,
    isDone = isDone
)

fun TaskDto.asTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    time = convertDateAndTimeStringsToLong(time, date),
    remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE.value.inWholeMilliseconds,
    isDone = isDone
)

// Helper Functions for models
//Combine date and time strings to convert to long
fun convertDateAndTimeStringsToLong(
    timeString: String,
    dateString: String
): Long {
    val combined = "$dateString $timeString"
    return LocalDateTime.parse(combined,
        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    ).atZone(ZoneId.systemDefault())
        .toInstant().toEpochMilli()
}

// Convert time Response to LocalDateTime Object
fun Long.toDateTime(): LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochMilli(this),
    ZoneId.systemDefault()
)

// Extract date from LocalDateTime object and format as String
fun LocalDateTime.toFormattedDate(): String =
    this.toLocalDate()
        .format(DateTimeFormatter.ofPattern(
            "dd MMM yyyy")
        )

// Extract time from LocalDateTime object and format as String
fun LocalDateTime.toFormattedTime(): String =
    this.toLocalTime()
        .format(DateTimeFormatter.ofPattern(
            "h:mm a")
        )
