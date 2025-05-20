package com.example.taskyapplication.agenda.task.presentation

data class TaskUiState(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    val remindAt: Long,
    val isDone: Boolean
)