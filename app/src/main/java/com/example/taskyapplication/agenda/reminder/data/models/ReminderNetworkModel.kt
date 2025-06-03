package com.example.taskyapplication.agenda.reminder.data.models

import com.example.taskyapplication.agenda.data.model.AgendaItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ReminderNetworkModel(
    @SerialName("id")
    override val itemId: String,
    override val title: String,
    override val description: String,
    @SerialName("time")
    override val startTime: Long,
    @SerialName("remindAt")
    override val reminderTime: Long,
): AgendaItem


@Serializable
data class UpdateReminderBody(
    @SerialName("id")
    override val itemId: String,
    override val title: String,
    override val description: String,
    @SerialName("time")
    override val startTime: Long,
    @SerialName("remindAt")
    override val reminderTime: Long
): AgendaItem