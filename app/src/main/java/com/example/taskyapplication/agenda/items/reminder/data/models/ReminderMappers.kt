package com.example.taskyapplication.agenda.items.reminder.data.models

import com.example.taskyapplication.agenda.domain.combineDateAndTime
import com.example.taskyapplication.agenda.domain.getReminderOptionFromMillis
import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.domain.toTimeAsString
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.presentation.ReminderUiState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun ReminderNetworkModel.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = itemId,
        title = title,
        description = description,
        time = startTime,
        remindAt = reminderTime
    )
}

fun UpdateReminderNetworkModel.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = itemId,
        title = title,
        description = description,
        time = startTime,
        remindAt = reminderTime
    )
}

fun ReminderUiState.toUpdateReminderNetworkModel(): UpdateReminderNetworkModel {
//    val dateTime = LocalDateTime.of(
//        LocalDate.parse(
//            date,
//            DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM)
//        ),
//        LocalTime.parse(time, DateTimeFormatter.ofLocalizedTime(java.time.format.FormatStyle.SHORT))
//    )
//    val startTimeMillis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val startDateTime = combineDateAndTime(date, time)
    val reminderTimeMillis = startDateTime - remindAt.asLong

    return UpdateReminderNetworkModel(
        itemId = id,
        title = title,
        description = description,
        startTime = startDateTime,
        reminderTime = reminderTimeMillis
    )
}

fun ReminderUiState.toReminderNetworkModel(): ReminderNetworkModel {
    val dateTime = LocalDateTime.of(
        LocalDate.parse(
            date,
            DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM)
        ),
        LocalTime.parse(time, DateTimeFormatter.ofLocalizedTime(java.time.format.FormatStyle.SHORT))
    )
    val startTimeMillis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val reminderTimeMillis = startTimeMillis - remindAt.asLong

    return ReminderNetworkModel(
        itemId = id,
        title = title,
        description = description,
        startTime = startTimeMillis,
        reminderTime = reminderTimeMillis
    )
}

fun ReminderResponse.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt
    )
}

fun ReminderEntity.toReminderUiState(): ReminderUiState {
    return ReminderUiState(
        id = id,
        title = title,
        description = description,
        time = time.toTimeAsString(),
        date = time.toDateAsString(),
        remindAt = getReminderOptionFromMillis(time - remindAt),
        isEditingItem = false,
        isEditingDate = false,
        isEditingTime = false,
        isEditingReminder = false
    )
}

