package com.example.taskyapplication.agenda.reminder.data.models

data class Reminder(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
