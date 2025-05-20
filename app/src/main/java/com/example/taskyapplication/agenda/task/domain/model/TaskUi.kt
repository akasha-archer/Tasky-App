package com.example.taskyapplication.agenda.task.domain.model

data class TaskUiState(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
)