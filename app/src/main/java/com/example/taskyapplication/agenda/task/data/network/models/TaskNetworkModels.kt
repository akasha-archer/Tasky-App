package com.example.taskyapplication.agenda.task.data.network.models

import com.example.taskyapplication.agenda.data.model.AgendaItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskNetworkModel(
    @SerialName("id")
    override val eventId: String,
    override val title: String,
    override val description: String,
    @SerialName("time")
    override val startTime: Long,
    @SerialName("remindAt")
    override val reminderTime: Long,
    val isDone: Boolean
): AgendaItem

@Serializable
data class UpdateTaskBody(
    @SerialName("id")
    override val eventId: String,
    override val title: String,
    override val description: String,
    @SerialName("time")
    override val startTime: Long,
    @SerialName("remindAt")
    override val reminderTime: Long,
    val isDone: Boolean
): AgendaItem
