package com.example.taskyapplication.agenda.reminder.domain

import com.example.taskyapplication.agenda.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.reminder.data.models.UpdateReminderBody
import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.data.network.models.UpdateTaskBody
import com.example.taskyapplication.agenda.task.domain.TaskDto
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult

interface ReminderRepository {
    suspend fun createNewReminder(
        request: ReminderNetworkModel
    ): EmptyResult<DataError>

    suspend fun updateReminder(
        request: UpdateReminderBody
    ): EmptyResult<DataError>

    suspend fun getReminderById(
        reminderId: String
    ): ReminderDto

    suspend fun deleteReminder(
        reminderId: String
    ): EmptyResult<DataError>
}
