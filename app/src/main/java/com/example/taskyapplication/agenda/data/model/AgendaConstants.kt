package com.example.taskyapplication.agenda.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.taskyapplication.agenda.task.presentation.TaskUiState.Companion.REMINDER_ONE_DAY_BEFORE
import com.example.taskyapplication.agenda.task.presentation.TaskUiState.Companion.REMINDER_ONE_HOUR_BEFORE
import com.example.taskyapplication.agenda.task.presentation.TaskUiState.Companion.REMINDER_SIX_HOURS_BEFORE
import com.example.taskyapplication.agenda.task.presentation.TaskUiState.Companion.REMINDER_TEN_MINUTES_BEFORE
import com.example.taskyapplication.agenda.task.presentation.TaskUiState.Companion.REMINDER_THIRTY_MINUTES_BEFORE
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

enum class AgendaItemType(val type: String) {
    TASK("Task"),
    REMINDER("Reminder"),
    EVENT("Event")
}

enum class ReminderOptions(val value: Duration, val timeString: String) {
    TEN_MINUTES_BEFORE(10L.minutes, REMINDER_TEN_MINUTES_BEFORE),
    THIRTY_MINUTES_BEFORE(30L.minutes, REMINDER_THIRTY_MINUTES_BEFORE),
    ONE_HOUR_BEFORE(1L.hours, REMINDER_ONE_HOUR_BEFORE),
    SIX_HOURS_BEFORE(6L.hours, REMINDER_SIX_HOURS_BEFORE),
    ONE_DAY_BEFORE(1L.days, REMINDER_ONE_DAY_BEFORE),
}

data class ReminderTimeItem(
    val reminderTime: String,
    val selectedIcon: ImageVector? = null,
)

val reminderTimeList = listOf(
    ReminderTimeItem(REMINDER_THIRTY_MINUTES_BEFORE),
    ReminderTimeItem(REMINDER_TEN_MINUTES_BEFORE),
    ReminderTimeItem(REMINDER_ONE_HOUR_BEFORE),
    ReminderTimeItem(REMINDER_SIX_HOURS_BEFORE),
    ReminderTimeItem(REMINDER_ONE_DAY_BEFORE),
)