package com.example.taskyapplication.agenda.task.data

import kotlinx.serialization.Serializable

@Serializable
data class GetTaskResponse(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val remindAt: String,
    val isDone: Boolean
)