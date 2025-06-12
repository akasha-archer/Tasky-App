package com.example.taskyapplication.agenda.items.reminder.data.models

import com.example.taskyapplication.agenda.domain.combineDateAndTime
import com.example.taskyapplication.agenda.domain.convertDateAndTimeStringsToLong
import com.example.taskyapplication.agenda.domain.getReminderOptionFromMillis
import com.example.taskyapplication.agenda.domain.timeAsLong
import com.example.taskyapplication.agenda.domain.toDateTime
import com.example.taskyapplication.agenda.domain.toFormattedDate
import com.example.taskyapplication.agenda.domain.toFormattedTime
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.presentation.ReminderUiState

fun ReminderNetworkModel.toReminderEntity(): ReminderEntity {
    val timeAsString = startTime.toDateTime().toFormattedTime()
    val dateAsString = startTime.toDateTime().toFormattedDate()
    return ReminderEntity(
        id = itemId,
        title = title,
        description = description,
        date = dateAsString,
        time = timeAsString,
        remindAt = reminderTime
    )
}

fun UpdateReminderNetworkModel.toReminderEntity(): ReminderEntity {
    val timeAsString = startTime.toDateTime().toFormattedTime()
    val dateAsString = startTime.toDateTime().toFormattedDate()
    return ReminderEntity(
        id = itemId,
        title = title,
        description = description,
        date = dateAsString,
        time = timeAsString,
        remindAt = reminderTime
    )
}

fun ReminderResponse.toReminderEntity(): ReminderEntity {
    val timeAsString = time.toDateTime().toFormattedTime()
    val dateAsString = time.toDateTime().toFormattedDate()
    return ReminderEntity(
        id = id,
        title = title,
        description = description,
        date = dateAsString,
        time = timeAsString,
        remindAt = remindAt
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
//    val dateTime = LocalDateTime.of(
//        LocalDate.parse(
//            date,
//            DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM)
//        ),
//        LocalTime.parse(time, DateTimeFormatter.ofLocalizedTime(java.time.format.FormatStyle.SHORT))
//    )
//    val startTimeMillis = time.timeAsLong()
//    val reminderTimeMillis = startTimeMillis.minus(remindAt.asLong)

    return ReminderNetworkModel(
        itemId = id,
        title = title,
        description = description,
        startTime = convertDateAndTimeStringsToLong(time, date),
        reminderTime = remindAt.asLong
    )
}

fun ReminderEntity.toReminderUiState(): ReminderUiState {
    return ReminderUiState(
        id = id,
        title = title,
        description = description,
        time = time,
        date = time,
        remindAt = getReminderOptionFromMillis(time.timeAsLong() - remindAt)
    )
}
