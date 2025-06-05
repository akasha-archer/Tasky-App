package com.example.taskyapplication.agenda.items.task.data.network.models

data class GetTaskResponse(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
)
