package com.example.taskyapplication.agenda.items.reminder.domain

import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderResponse
import com.example.taskyapplication.agenda.items.reminder.data.models.UpdateReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.domain.network.ReminderApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.safeApiCall
import retrofit2.Response
import javax.inject.Inject

interface ReminderRemoteDataSource {
    suspend fun getReminder(reminderId: String): Response<ReminderResponse>
    suspend fun createReminder(reminder: ReminderNetworkModel): Response<Unit>
    suspend fun updateReminder(reminder: UpdateReminderNetworkModel): Response<Unit>
    suspend fun deleteReminder(reminderId: String): Response<Unit>
}

class ReminderRemoteDataSourceImpl @Inject constructor(
    private val reminderApiService: ReminderApiService
): ReminderRemoteDataSource {

    override suspend fun getReminder(reminderId: String): Response<ReminderResponse> {
        return reminderApiService.getReminderById(reminderId)
    }

    override suspend fun createReminder(reminder: ReminderNetworkModel): Response<Unit> {
        return reminderApiService.createNewReminder(reminder)
    }

    override suspend fun updateReminder(reminder: UpdateReminderNetworkModel): Response<Unit> {
        return reminderApiService.updateReminder(reminder)
    }

    override suspend fun deleteReminder(reminderId: String): Response<Unit> {
        return reminderApiService.deleteReminderById(reminderId)
    }
}
