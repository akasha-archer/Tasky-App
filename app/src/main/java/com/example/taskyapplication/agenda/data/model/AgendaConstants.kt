package com.example.taskyapplication.agenda.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

enum class AgendaTypes(val type: String) {
    TASK("Task"),
    REMINDER("Reminder"),
    EVENT("Event")
}

enum class ReminderOptions(val value: Duration) {
    TEN_MINUTES_BEFORE(10L.minutes),
    THIRTY_MINUTES_BEFORE(30L.minutes),
    ONE_HOUR_BEFORE(1L.hours),
    SIX_HOURS_BEFORE(6L.hours),
    ONE_DAY_BEFORE(1L.days),
}

data class ReminderTimeItem(
    val reminderTime: String,
    val selectedIcon: ImageVector? = null,
)

val reminderTimeList = listOf(
    ReminderTimeItem(reminderTime = ReminderOptions.TEN_MINUTES_BEFORE.value.toString()),
    ReminderTimeItem(reminderTime = ReminderOptions.THIRTY_MINUTES_BEFORE.value.toString()),
    ReminderTimeItem(reminderTime = ReminderOptions.ONE_HOUR_BEFORE.value.toString()),
    ReminderTimeItem(reminderTime = ReminderOptions.SIX_HOURS_BEFORE.value.toString()),
    ReminderTimeItem(reminderTime = ReminderOptions.ONE_DAY_BEFORE.value.toString()),
)