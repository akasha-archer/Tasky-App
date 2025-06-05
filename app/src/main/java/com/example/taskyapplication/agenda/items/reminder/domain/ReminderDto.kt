package com.example.taskyapplication.agenda.items.reminder.domain

data class ReminderDto(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val date: Long,
    val remindAt: Long,
)
