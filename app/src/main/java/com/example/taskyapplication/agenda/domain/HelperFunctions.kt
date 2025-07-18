package com.example.taskyapplication.agenda.domain

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toInitials(): String {
    if (this.isBlank()) return ""
    val nameList = this.split(" ")
    val firstInitial = nameList.firstOrNull()?.get(0)
    val lastInitial = nameList.lastOrNull()?.get(0)
    return (firstInitial?.uppercase() ?: "") + (lastInitial?.uppercase() ?: "")
}

fun combineLocalDateAndTime(date: LocalDate, time: LocalTime): LocalDateTime {
    return LocalDateTime.of(date, time)
}

fun LocalDateTime.convertToLong(): Long {
    return this.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli()
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
    return LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.of("UTC"))
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun Long.toTimeAsString(): String =
    LocalTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.of("UTC"))
        .format(DateTimeFormatter.ofPattern("h:mm a"))

data class AgendaScreenCalendarData(
    val dayOfWeek: String,
    val dayOfMonth: String,
    val dateValue: Long
)

fun buildAgendaScreenCalendar(): List<AgendaScreenCalendarData> {
    val daysBefore = NUM_DAYS_BEFORE_AND_AFTER
    val daysAfter = NUM_DAYS_BEFORE_AND_AFTER

    val today = LocalDate.now()
    val dateListWithDate = mutableListOf<AgendaScreenCalendarData>()
    val dateFormatter = DateTimeFormatter.ofPattern("d", Locale.getDefault())
    val dayFormatter = DateTimeFormatter.ofPattern("E", Locale.getDefault())

    // Loop from 15 days before to 15 days after today
    for (i in -daysBefore..daysAfter) {
        val date = today.plusDays(i)
        dateListWithDate.add(
            AgendaScreenCalendarData(
                dayOfWeek = date.format(dayFormatter),
                dayOfMonth = date.format(dateFormatter),
                dateValue = date.toEpochDay()
            )
        )
    }
    return dateListWithDate
}

const val NUM_DAYS_BEFORE_AND_AFTER = 15L