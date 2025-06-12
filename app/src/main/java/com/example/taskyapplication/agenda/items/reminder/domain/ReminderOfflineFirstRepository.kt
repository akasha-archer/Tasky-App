package com.example.taskyapplication.agenda.items.reminder.domain

import android.util.Log
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.UpdateReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.toReminderEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

class ReminderOfflineFirstRepository @Inject constructor(
    private val localDataSource: ReminderLocalDataSource,
    private val remoteDataSource: ReminderRemoteDataSource,
    private val applicationScope: CoroutineScope,
) : ReminderRepository {

    override suspend fun createNewReminder(request: ReminderNetworkModel): EmptyResult<DataError> {
        val localResult = localDataSource.upsertReminder(request.toReminderEntity())
        if (localResult !is Result.Success) {
            Log.e("Error inserting new reminder", "error: $localResult")
            return localResult.asEmptyDataResult()
        }

        val remoteResult = remoteDataSource.createReminder(request)
        return when (remoteResult) {
            is Result.Error -> {
                Log.e("Error creating reminder", remoteResult.error.toString())
                Result.Error(remoteResult.error)
            }

            is Result.Success -> {
                Log.e("Reminder Repository", "Reminder created successfully")
                Result.Success(Unit)
            }
        }
    }

    override suspend fun updateReminder(request: UpdateReminderNetworkModel): EmptyResult<DataError> {
        val localResult = localDataSource.upsertReminder(request.toReminderEntity())
        val remoteResult = remoteDataSource.updateReminder(request)
        return when (remoteResult) {
            is Result.Error -> {
                Log.e("Error updating reminder", remoteResult.error.toString())
                Result.Error(remoteResult.error)
            }

            is Result.Success -> {
                Log.e("Reminder Repository", "Reminder updated successfully")
                Result.Success(Unit)
            }
        }
    }

    override suspend fun getReminderById(reminderId: String): ReminderEntity {
        return localDataSource.getReminder(reminderId)
    }



    override suspend fun deleteReminder(reminderId: String): EmptyResult<DataError> {
        localDataSource.deleteReminder(reminderId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteReminder(reminderId)
        }.await()
        if (remoteResult !is Result.Success) {
            Log.e("Reminder Repository", "error deleting reminder: $remoteResult")

        }
        return remoteResult.asEmptyDataResult()
    }
}
