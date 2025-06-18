package com.example.taskyapplication.agenda.items.task.data.network.models

import com.example.taskyapplication.agenda.data.model.AgendaItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
//@SerialName("task")
data class TaskNetworkModel(
    @SerialName("id")
    val itemId: String,
    val title: String,
    val description: String,
    @SerialName("time")
    val startTime: Long,
    @SerialName("remindAt")
    val reminderTime: Long,
    val isDone: Boolean
)

@Serializable
data class UpdateTaskBody(
    @SerialName("id")
    override val itemId: String,
    override val title: String,
    override val description: String,
    @SerialName("time")
    override val startTime: Long,
    @SerialName("remindAt")
    override val reminderTime: Long,
    val isDone: Boolean
): AgendaItem
