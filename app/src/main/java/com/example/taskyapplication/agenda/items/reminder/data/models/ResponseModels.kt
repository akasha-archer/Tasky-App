package com.example.taskyapplication.agenda.items.reminder.data.models

data class ReminderResponse(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
