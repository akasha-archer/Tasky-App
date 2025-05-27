package com.example.taskyapplication.agenda.data.model

enum class AgendaTypes(val type: String) {
    TASK("Task"),
    REMINDER("Reminder"),
    EVENT("Event")
}

enum class ReminderOptions(val value: String) {
    TEN_MINUTES_BEFORE("10 minutes before"),
    THIRTY_MINUTES_BEFORE("30 minutes before"),
    ONE_HOUR_BEFORE("1 hour before"),
    SIX_HOURS_BEFORE("6 hours before"),
    ONE_DAY_BEFORE("1 day before"),
}