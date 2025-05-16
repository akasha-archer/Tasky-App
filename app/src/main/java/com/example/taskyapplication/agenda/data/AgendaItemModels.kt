package com.example.taskyapplication.agenda.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

interface AgendaItem {
    val eventId: String
    val title: String
    val description: String
    val startTime: String
    val reminderTime: String
}

@Serializable
data class Task(
    @SerialName("id")
    override val eventId: String,
    override val title: String,
    override val description: String,
    @SerialName("time")
    override val startTime: String,
    @SerialName("remindAt")
    override val reminderTime: String,
    val isDone: Boolean
): AgendaItem

@Serializable
data class UpdateTaskData(
    @SerialName("id")
    override val eventId: String,
    override val title: String,
    override val description: String,
    @SerialName("time")
    override val startTime: String,
    @SerialName("remindAt")
    override val reminderTime: String,
    val isDone: Boolean
): AgendaItem

data class TaskState(
    val taskId: String? =  UUID.randomUUID().toString(),
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskStartTime: String = "",
    val taskStartDate: String = "",
    val taskReminderTime: String = "",
    val isTaskDone: Boolean = false,
)
