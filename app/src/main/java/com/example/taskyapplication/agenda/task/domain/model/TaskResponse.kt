package com.example.taskyapplication.agenda.task.domain.model

data class GetTaskResponse(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val remindAt: String,
    val isDone: Boolean
)
