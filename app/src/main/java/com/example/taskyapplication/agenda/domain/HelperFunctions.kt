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
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale


fun combineLocalDateAndTime(date: LocalDate, time: LocalTime): LocalDateTime {
    return LocalDateTime.of(date, time)
}

fun LocalDateTime.convertToLong(): Long {
    return this.atZone(ZoneId.of("America/New_York")).toInstant().toEpochMilli()
}

fun String.toInitials(): String {
    return this.split(" ").mapNotNull { it.firstOrNull()?.toString() }.reduce { acc, s -> acc + s }
}

fun Long.toLocalDateAndTime(): Pair<LocalDate, LocalTime> {
    val instant = Instant.ofEpochMilli(this)
    val zonedDateTimeUTC = instant.atZone(ZoneOffset.UTC)
    val localDate = zonedDateTimeUTC.toLocalDate()
    val localTime = zonedDateTimeUTC.toLocalTime()
    return Pair(localDate, localTime)
}

fun LocalDate.toDateAsString(): String =
    this.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

fun LocalTime.toTimeAsString(): String =
    this.format(DateTimeFormatter.ofPattern("h:mm a"))

fun Long.toDateAsString(): String {
    return LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.of("America/New_York"))
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun Long.toTimeAsString(): String =
    LocalTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.of("America/New_York"))
        .format(DateTimeFormatter.ofPattern("h:mm a"))

fun String.timeAsLong(): Long {
    val simpleDate = SimpleDateFormat("h:mm a", Locale.getDefault())
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

data class AgendaScreenCalendarList(
    val dayOfWeek: String,
    val dayOfMonth: String,
    val dateValue: Long
)

fun buildAgendaScreenCalendar(): List<AgendaScreenCalendarList> {
    val daysBefore = 15L
    val daysAfter = 15L

    val today = LocalDate.now()
    val dateListWithDate = mutableListOf<AgendaScreenCalendarList>()
    val dateFormatter = DateTimeFormatter.ofPattern("d", Locale.getDefault())
    val dayFormatter = DateTimeFormatter.ofPattern("E", Locale.getDefault())

    // Loop from 15 days before to 15 days after today
    for (i in -daysBefore..daysAfter) {
        val date = today.plusDays(i)
        dateListWithDate.add(
            AgendaScreenCalendarList(
                dayOfWeek = date.format(dayFormatter),
                dayOfMonth = date.format(dateFormatter),
                dateValue = date.toEpochDay()
            )
        )
    }
    return dateListWithDate
}
