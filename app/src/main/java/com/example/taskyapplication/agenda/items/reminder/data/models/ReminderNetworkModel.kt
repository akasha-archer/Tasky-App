package com.example.taskyapplication.agenda.items.reminder.data.models

import com.example.taskyapplication.agenda.data.model.AgendaItem
import kotlinx.serialization.SerialName

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

data class UpdateReminderNetworkModel(
    @SerialName("id")
    override val itemId: String,
    override val title: String,
    override val description: String,
    @SerialName("time")
    override val startTime: Long,
    @SerialName("remindAt")
    override val reminderTime: Long
): AgendaItem