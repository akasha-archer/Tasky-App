package com.example.taskyapplication.agenda.task.domain

import com.example.taskyapplication.agenda.data.AgendaItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskRequestBody(
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
data class UpdateTaskBody(
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

