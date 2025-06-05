package com.example.taskyapplication.agenda.data.model

import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_ONE_DAY_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_ONE_HOUR_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_SIX_HOURS_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_TEN_MINUTES_BEFORE
import com.example.taskyapplication.agenda.data.model.ReminderTimeItem.Companion.REMINDER_THIRTY_MINUTES_BEFORE
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

enum class ReminderOptions(val value: Duration, val timeString: String, val asLong: Long) {
    TEN_MINUTES_BEFORE(10L.minutes, REMINDER_TEN_MINUTES_BEFORE, 10L.minutes.toLong(DurationUnit.MILLISECONDS)),
    THIRTY_MINUTES_BEFORE(30L.minutes, REMINDER_THIRTY_MINUTES_BEFORE, 30L.minutes.toLong(DurationUnit.MILLISECONDS)),
    ONE_HOUR_BEFORE(1L.hours, REMINDER_ONE_HOUR_BEFORE, 1L.hours.toLong(DurationUnit.MILLISECONDS)),
    SIX_HOURS_BEFORE(6L.hours, REMINDER_SIX_HOURS_BEFORE, 6L.hours.toLong(DurationUnit.MILLISECONDS)),
    ONE_DAY_BEFORE(1L.days, REMINDER_ONE_DAY_BEFORE, 1L.days.toLong(DurationUnit.MILLISECONDS)),
}

data class ReminderTimeItem(
    val reminderTime: String,
) {
    companion object {
        const val REMINDER_THIRTY_MINUTES_BEFORE = "30 minutes before"
        const val REMINDER_TEN_MINUTES_BEFORE = "10 minutes before"
        const val REMINDER_ONE_HOUR_BEFORE = "1 hour before"
        const val REMINDER_ONE_DAY_BEFORE = "1 day before"
        const val REMINDER_SIX_HOURS_BEFORE = "6 hours before"
    }
}

val reminderTimeList = listOf(
    ReminderTimeItem(REMINDER_THIRTY_MINUTES_BEFORE),
    ReminderTimeItem(REMINDER_TEN_MINUTES_BEFORE),
    ReminderTimeItem(REMINDER_ONE_HOUR_BEFORE),
    ReminderTimeItem(REMINDER_SIX_HOURS_BEFORE),
    ReminderTimeItem(REMINDER_ONE_DAY_BEFORE),
)