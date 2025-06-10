package com.example.taskyapplication.agenda.domain

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_ONE_DAY_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_ONE_HOUR_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_SIX_HOURS_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_TEN_MINUTES_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_THIRTY_MINUTES_BEFORE
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toInitials(): String {
    return this.split(" ").mapNotNull { it.firstOrNull()?.toString() }.reduce { acc, s -> acc + s }
}

fun combineDateAndTime(date: String, time: String): Long {
    val dateTime = LocalDateTime.of(
        LocalDate.parse(
            date,
            DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.MEDIUM)
        ),
        LocalTime.parse(time, DateTimeFormatter.ofLocalizedTime(java.time.format.FormatStyle.SHORT))
    )
    return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun calculateNotificationTime(reminderTime: Long, startTime: Long) = startTime - reminderTime

fun Long.toDateAsString(): String =
    LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

fun Long.toTimeAsString(): String =
    LocalTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("h:mm a"))

fun String.timeAsLong(): Long {
    val simpleDate = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDate.parse(this)?.time
        ?: throw IllegalArgumentException("Invalid time string or pattern")
}

fun getReminderOption(
    selection: String,
): ReminderOptions {
    return when (selection) {
        REMINDER_TEN_MINUTES_BEFORE -> ReminderOptions.TEN_MINUTES_BEFORE
        REMINDER_THIRTY_MINUTES_BEFORE -> ReminderOptions.THIRTY_MINUTES_BEFORE
        REMINDER_ONE_HOUR_BEFORE -> ReminderOptions.ONE_HOUR_BEFORE
        REMINDER_ONE_DAY_BEFORE -> ReminderOptions.ONE_DAY_BEFORE
        REMINDER_SIX_HOURS_BEFORE -> ReminderOptions.SIX_HOURS_BEFORE
        else -> ReminderOptions.THIRTY_MINUTES_BEFORE // default value
    }
}

fun getReminderOptionFromMillis(millis: Long): ReminderOptions {
    return when (millis) {
        ReminderOptions.TEN_MINUTES_BEFORE.asLong -> ReminderOptions.TEN_MINUTES_BEFORE
        ReminderOptions.THIRTY_MINUTES_BEFORE.asLong -> ReminderOptions.THIRTY_MINUTES_BEFORE
        ReminderOptions.ONE_HOUR_BEFORE.asLong -> ReminderOptions.ONE_HOUR_BEFORE
        ReminderOptions.SIX_HOURS_BEFORE.asLong -> ReminderOptions.SIX_HOURS_BEFORE
        ReminderOptions.ONE_DAY_BEFORE.asLong -> ReminderOptions.ONE_DAY_BEFORE
        else -> ReminderOptions.THIRTY_MINUTES_BEFORE // default value
    }
}

fun buildAgendaScreenCalendar(): List<Pair<String, String>> {
    val daysBefore = 15L
    val daysAfter = 15L

    // today's date
    val today = LocalDate.now()

    // Create a list to hold the formatted dates
    val dateList = mutableListOf<Pair<String, String>>()

    // Define the desired date format (e.g., "Mon 26")
    val dateFormatter = DateTimeFormatter.ofPattern("d", Locale.getDefault())
    val dayFormatter = DateTimeFormatter.ofPattern("E", Locale.getDefault())

    // Loop from 15 days before to 15 days after today
    for (i in -daysBefore..daysAfter) {
        val date = today.plusDays(i)
        dateList.add(Pair(date.format(dayFormatter), date.format(dateFormatter)))
    }
    return dateList
}
