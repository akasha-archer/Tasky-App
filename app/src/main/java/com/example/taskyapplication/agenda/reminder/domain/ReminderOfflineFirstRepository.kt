package com.example.taskyapplication.agenda.reminder.domain

import com.example.taskyapplication.agenda.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.reminder.data.models.UpdateReminderBody
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ReminderOfflineFirstRepository @Inject constructor(
    private val localDataSource: ReminderLocalDataSource,
    private val remoteDataSource: ReminderRemoteDataSource,
    private val applicationScope: CoroutineScope,
): ReminderRepository {
    override suspend fun createNewReminder(request: ReminderNetworkModel): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateReminder(request: UpdateReminderBody): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getReminderById(reminderId: String): ReminderDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReminder(reminderId: String): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }
}