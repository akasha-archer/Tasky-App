package com.example.taskyapplication.agenda.items.reminder.data.models

import com.example.taskyapplication.agenda.domain.getReminderOptionFromMillis
import com.example.taskyapplication.agenda.domain.toLocalDateAndTime
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.presentation.ReminderUiState
import java.time.LocalDateTime
import java.time.ZoneId

fun ReminderNetworkModel.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = itemId,
        title = title,
        description = description,
        date = startTime.toLocalDateAndTime().first,
        time = startTime.toLocalDateAndTime().second,
        remindAt = reminderTime
    )
}

fun UpdateReminderNetworkModel.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = itemId,
        title = title,
        description = description,
        date = startTime.toLocalDateAndTime().first,
        time = startTime.toLocalDateAndTime().second,
        remindAt = reminderTime
    )
}

fun ReminderResponse.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        title = title,
        description = description,
        date = time.toLocalDateAndTime().first,
        time = time.toLocalDateAndTime().second,
        remindAt = remindAt
    )
}

fun ReminderUiState.toUpdateReminderNetworkModel(): UpdateReminderNetworkModel {
    val combinedStartTime = LocalDateTime.of(date, time)
    val startTimeAsLong = combinedStartTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val reminderTimeMillis = startTimeAsLong - remindAt.asLong

    return UpdateReminderNetworkModel(
        itemId = id,
        title = title,
        description = description,
        startTime = startTimeAsLong,
        reminderTime = reminderTimeMillis
    )
}

fun ReminderUiState.toReminderNetworkModel(): ReminderNetworkModel {
    val combinedStartTime = LocalDateTime.of(date, time)
    val startTimeAsLong = combinedStartTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val reminderTimeMillis = startTimeAsLong - remindAt.asLong

    return ReminderNetworkModel(
        itemId = id,
        title = title,
        description = description,
        startTime = startTimeAsLong,
        reminderTime = reminderTimeMillis
    )
}

fun ReminderEntity.toReminderUiState(): ReminderUiState {
    return ReminderUiState(
        id = id,
        title = title,
        description = description,
        time = time,
        date = date,
        remindAt = getReminderOptionFromMillis(remindAt)
    )
}
