package com.example.taskyapplication.agenda.items.reminder.domain

import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.UpdateReminderNetworkModel
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult

interface ReminderRepository {
    suspend fun createNewReminder(
        request: ReminderNetworkModel
    ): kotlin.Result<Unit>

    suspend fun updateReminder(
        request: UpdateReminderNetworkModel
    ): kotlin.Result<Unit>

    suspend fun getReminderById(
        reminderId: String
    ): ReminderEntity?

    suspend fun deleteReminder(
        reminderId: String
    ):kotlin.Result<Unit>
}
