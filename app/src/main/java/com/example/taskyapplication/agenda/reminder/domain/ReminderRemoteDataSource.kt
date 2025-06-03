package com.example.taskyapplication.agenda.reminder.domain

import com.example.taskyapplication.agenda.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.reminder.data.models.ReminderResponse
import com.example.taskyapplication.agenda.reminder.data.models.UpdateReminderBody
import com.example.taskyapplication.agenda.reminder.domain.network.ReminderApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result

interface ReminderRemoteDataSource {
    suspend fun getReminder(reminderId: String): Result<ReminderResponse, DataError.Network>
    suspend fun postReminder(reminder: ReminderNetworkModel): EmptyResult<DataError>
    suspend fun updateReminder(reminder: UpdateReminderBody): EmptyResult<DataError>
    suspend fun deleteReminder(reminderId: String): EmptyResult<DataError>
}

class ReminderRemoteDataSourceImpl(
    reminderApiService: ReminderApiService
): ReminderRemoteDataSource {

    override suspend fun getReminder(reminderId: String): Result<ReminderResponse, DataError.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun postReminder(reminder: ReminderNetworkModel): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateReminder(reminder: UpdateReminderBody): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReminder(reminderId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }
}
