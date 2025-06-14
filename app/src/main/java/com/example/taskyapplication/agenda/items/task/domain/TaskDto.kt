package com.example.taskyapplication.agenda.items.task.domain

import com.example.taskyapplication.agenda.data.model.ReminderOptions

data class TaskDto(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val remindAt: ReminderOptions,
    val isDone: Boolean
)
