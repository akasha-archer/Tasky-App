package com.example.taskyapplication.agenda.data.model

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.taskyapplication.R
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
    TEN_MINUTES_BEFORE(10L.minutes, Resources.getSystem().getString(R.string.reminder_ten_minutes)),
    THIRTY_MINUTES_BEFORE(30L.minutes, Resources.getSystem().getString(R.string.reminder_thirty_minutes)),
    ONE_HOUR_BEFORE(1L.hours, Resources.getSystem().getString(R.string.reminder_one_hour)),
    SIX_HOURS_BEFORE(6L.hours, Resources.getSystem().getString(R.string.reminder_six_hours)),
    ONE_DAY_BEFORE(1L.days, Resources.getSystem().getString(R.string.reminder_one_day)),
}

data class ReminderTimeItem(
    val reminderTime: String,
    val selectedIcon: ImageVector? = null,
)

val reminderTimeList = listOf(
    ReminderTimeItem(reminderTime = ReminderOptions.THIRTY_MINUTES_BEFORE.timeString),
    ReminderTimeItem(reminderTime = ReminderOptions.TEN_MINUTES_BEFORE.timeString),
    ReminderTimeItem(reminderTime = ReminderOptions.ONE_HOUR_BEFORE.timeString),
    ReminderTimeItem(reminderTime = ReminderOptions.SIX_HOURS_BEFORE.timeString),
    ReminderTimeItem(reminderTime = ReminderOptions.ONE_DAY_BEFORE.timeString),
)