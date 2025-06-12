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

//Combine date and time strings to convert to long
fun convertDateAndTimeStringsToLong(
    timeString: String,
    dateString: String
): Long {
    val combined = "$dateString $timeString"
    return LocalDateTime.parse(
        combined,
        DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a")
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
        .format(
            DateTimeFormatter.ofPattern(
                "dd MMM yyyy"
            )
        )

// Extract time from LocalDateTime object and format as String
fun LocalDateTime.toFormattedTime(): String =
    this.toLocalTime()
        .format(
            DateTimeFormatter.ofPattern(
                "h:mm a"
            )
        )


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

fun Long.toDayMonthAsString(): String =
    LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd MMM"))
//
//fun Long.toDateAsString(): String {
//    val dateToConvert = if (this < System.currentTimeMillis()) {
//        System.currentTimeMillis()
//    } else {
//        this
//    }
//
//    return if (dateToConvert == System.currentTimeMillis()) "Today" else
//     LocalDate.ofInstant(Instant.ofEpochMilli(dateToConvert), ZoneId.systemDefault())
//        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
//}

fun Long.toDateAsString(): String {
    return LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}


fun Long.toTimeAsString(): String =
    LocalTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
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

    // today's date
    val today = LocalDate.now()

//    val dateList = mutableListOf<Pair<String, String>>()
    val dateListWithDate = mutableListOf<AgendaScreenCalendarList>()

    // Define the desired date format (e.g., "Mon 26")
    val dateFormatter = DateTimeFormatter.ofPattern("d", Locale.getDefault())
    val dayFormatter = DateTimeFormatter.ofPattern("E", Locale.getDefault())

    // Loop from 15 days before to 15 days after today
    for (i in -daysBefore..daysAfter) {
        val date = today.plusDays(i)
//        dateList.add(Pair(date.format(dayFormatter), date.format(dateFormatter)))
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
