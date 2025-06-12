package com.example.taskyapplication.agenda.items.reminder.domain

import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderResponse
import com.example.taskyapplication.agenda.items.reminder.data.models.UpdateReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.domain.network.ReminderApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.safeApiCall
import javax.inject.Inject

interface ReminderRemoteDataSource {
    suspend fun getReminder(reminderId: String): Result<ReminderResponse, DataError.Network>
    suspend fun createReminder(reminder: ReminderNetworkModel): EmptyResult<DataError>
    suspend fun updateReminder(reminder: UpdateReminderNetworkModel): EmptyResult<DataError>
    suspend fun deleteReminder(reminderId: String): EmptyResult<DataError>
}

class ReminderRemoteDataSourceImpl @Inject constructor(
    private val reminderApiService: ReminderApiService
): ReminderRemoteDataSource {

    override suspend fun getReminder(reminderId: String): Result<ReminderResponse, DataError.Network> {
        return safeApiCall {
            reminderApiService.getReminderById(reminderId)
        }
    }

    override suspend fun createReminder(reminder: ReminderNetworkModel): EmptyResult<DataError> {
        return safeApiCall {
            reminderApiService.createNewReminder(reminder)
        }
    }

    override suspend fun updateReminder(reminder: UpdateReminderNetworkModel): EmptyResult<DataError> {
        return safeApiCall {
            reminderApiService.updateReminder(reminder)
        }
    }

    override suspend fun deleteReminder(reminderId: String): EmptyResult<DataError> {
        return safeApiCall {
            reminderApiService.deleteReminderById(reminderId)
        }
    }
}
