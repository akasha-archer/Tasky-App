package com.example.taskyapplication.agenda.data.model

import com.example.taskyapplication.agenda.domain.convertToLong
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

enum class VisitorStatus(val value: String) {
    ALL("All"),
    GOING("Going"),
    NOT_GOING("Not Going")
}

const val TEN_MINUTES = 10
const val THIRTY_MINUTES = 30
const val ONE_HOUR = 60
const val SIX_HOURS = 360
const val ONE_DAY = 1440


enum class ReminderNotificationOption(val timeString: String, val minutesAsInt: Int) {
    TEN_MINUTES_BEFORE("10 minutes before", TEN_MINUTES),
    THIRTY_MINUTES_BEFORE("30 minutes before", THIRTY_MINUTES),
    ONE_HOUR_BEFORE("1 hour before", ONE_HOUR),
    SIX_HOURS_BEFORE("6 hours before", SIX_HOURS),
    ONE_DAY_BEFORE("1 day before", ONE_DAY),
}

val reminderTimeList = listOf(
    ReminderNotificationOption.THIRTY_MINUTES_BEFORE.timeString,
    ReminderNotificationOption.TEN_MINUTES_BEFORE.timeString,
    ReminderNotificationOption.ONE_HOUR_BEFORE.timeString,
    ReminderNotificationOption.SIX_HOURS_BEFORE.timeString,
    ReminderNotificationOption.ONE_DAY_BEFORE.timeString,
)

fun getReminderNotificationFromString(
    selection: String,
): ReminderNotificationOption {
    return when (selection) {
        ReminderNotificationOption.THIRTY_MINUTES_BEFORE.timeString -> ReminderNotificationOption.THIRTY_MINUTES_BEFORE
        ReminderNotificationOption.TEN_MINUTES_BEFORE.timeString -> ReminderNotificationOption.TEN_MINUTES_BEFORE
        ReminderNotificationOption.ONE_HOUR_BEFORE.timeString -> ReminderNotificationOption.ONE_HOUR_BEFORE
        ReminderNotificationOption.SIX_HOURS_BEFORE.timeString -> ReminderNotificationOption.SIX_HOURS_BEFORE
        ReminderNotificationOption.ONE_DAY_BEFORE.timeString -> ReminderNotificationOption.ONE_DAY_BEFORE
        else -> ReminderNotificationOption.THIRTY_MINUTES_BEFORE // default value
    }
}

fun getReminderNotificationFromLong(
    startTime: LocalTime,
    startDate: LocalDate,
    reminderMillis: Long
): ReminderNotificationOption {
    val from = LocalDateTime.of(startDate, startTime).convertToLong() - reminderMillis
    return when(from) {
        TEN_MINUTES.toLong() -> ReminderNotificationOption.TEN_MINUTES_BEFORE
        THIRTY_MINUTES.toLong() -> ReminderNotificationOption.THIRTY_MINUTES_BEFORE
        ONE_HOUR.toLong() -> ReminderNotificationOption.ONE_HOUR_BEFORE
        SIX_HOURS.toLong() -> ReminderNotificationOption.SIX_HOURS_BEFORE
        ONE_DAY.toLong() -> ReminderNotificationOption.ONE_DAY_BEFORE
        else -> ReminderNotificationOption.THIRTY_MINUTES_BEFORE
    }
}
