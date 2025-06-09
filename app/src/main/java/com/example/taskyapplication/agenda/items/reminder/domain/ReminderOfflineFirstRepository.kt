package com.example.taskyapplication.agenda.items.reminder.domain

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
): ReminderRepository {

    override suspend fun createNewReminder(request: ReminderNetworkModel): EmptyResult<DataError> {
        val remoteResult = remoteDataSource.postReminder(request)
        return when (remoteResult) {
            is Result.Error -> {
                TODO("insert to pending table")
            }
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertReminder(request.toReminderEntity())
                }.await()
            }
        }
    }

    override suspend fun updateReminder(request: UpdateReminderNetworkModel): EmptyResult<DataError> {
        val remoteResult = remoteDataSource.updateReminder(request)
        return when (remoteResult) {
            is Result.Error -> {
                TODO("insert to pending table")
            }
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertReminder(request.toReminderEntity())
                }.await()
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
            TODO("insert to pending table")
        }
        return remoteResult.asEmptyDataResult()
    }
}
