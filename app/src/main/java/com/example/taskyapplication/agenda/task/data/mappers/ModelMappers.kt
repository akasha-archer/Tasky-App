package com.example.taskyapplication.agenda.task.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskyapplication.agenda.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.task.data.network.models.GetTaskResponse
import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.task.presentation.TaskUiState
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

@RequiresApi(Build.VERSION_CODES.O)
fun TaskUiState.asTaskNetworkModel() = TaskNetworkModel(
    itemId = id,
    title = title,
    description = description,
    startTime = convertDateAndTimeStringsToLong(time, date),
    reminderTime = 0L,
    isDone = isDone
)

@RequiresApi(Build.VERSION_CODES.O)
fun TaskUiState.asUpdateTaskModel() = UpdateTaskBody(
    itemId = id,
    title = title,
    description = description,
    startTime = convertDateAndTimeStringsToLong(time, date),
    reminderTime = 0L,
    isDone = isDone
)

@RequiresApi(Build.VERSION_CODES.O)
fun TaskUiState.asEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    time = convertDateAndTimeStringsToLong(time, date),
    remindAt = remindAt,
    isDone = isDone
)

// Helper Functions for models

//Combine date and time strings to convert to long
@RequiresApi(Build.VERSION_CODES.O)
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
@RequiresApi(Build.VERSION_CODES.O)
fun Long.toDateTime(): LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochMilli(this),
    ZoneId.systemDefault()
)

// Extract date from LocalDateTime object and format as String
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toFormattedDate(): String =
    this.toLocalDate()
        .format(DateTimeFormatter.ofPattern(
            "dd MMM yyyy")
        )

// Extract time from LocalDateTime object and format as String
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toFormattedTime(): String =
    this.toLocalTime()
        .format(DateTimeFormatter.ofPattern(
            "h:mm a")
        )
