package com.example.taskyapplication.agenda.items.reminder.domain

import android.util.Log
import com.example.taskyapplication.agenda.items.reminder.data.db.DeletedReminderIdEntity
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.UpdateReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.toReminderEntity
import com.example.taskyapplication.domain.utils.SUCCESS_CODE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

class ReminderOfflineFirstRepository @Inject constructor(
    private val localDataSource: ReminderLocalDataSource,
    private val remoteDataSource: ReminderRemoteDataSource,
    private val applicationScope: CoroutineScope,
) : ReminderRepository {

    override suspend fun createNewReminder(request: ReminderNetworkModel): kotlin.Result<Unit> {
        val localResult = localDataSource.upsertReminder(request.toReminderEntity())
        if (localResult != kotlin.Result.success(Unit)) {
            Log.e("Reminder Repository: Error inserting new reminder", "error: $localResult")
        } else {
            Log.i("Reminder Repository: Finished posting to database", "local storage success")
        }

        return try {
            val remoteResult = remoteDataSource.createReminder(request)
            if (remoteResult.code() == SUCCESS_CODE) {
                Log.e(
                    "Reminder Repository: Error creating ${request.title} reminder",
                    remoteResult.toString()
                )
            } else {
                Log.i("Reminder Repository:", "Reminder created successfully")
            }
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Reminder Repo:", e.message.toString())
            kotlin.Result.failure(e)
        }
    }

    override suspend fun updateReminder(request: UpdateReminderNetworkModel): kotlin.Result<Unit> {
        val localResult = localDataSource.upsertReminder(request.toReminderEntity())
        if (localResult != kotlin.Result.success(Unit)) {
            Log.e("Reminder Repository: Error updating reminder", "error: $localResult")
        }

        return try {
            val remoteResult = remoteDataSource.updateReminder(request)
            if (remoteResult.code() == SUCCESS_CODE) {
                Log.i("Reminder Repository:", "Reminder updated successfully")
            } else {
                Log.e(
                    "Reminder Repository: Error updating ${request.title} reminder",
                    remoteResult.toString()
                )
            }
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Task Repo:", e.message.toString())
            kotlin.Result.failure(e)
        }
    }

    override suspend fun getReminderById(reminderId: String): ReminderEntity? {
        return localDataSource.getReminder(reminderId)
    }

    override suspend fun deleteReminder(reminderId: String): kotlin.Result<Unit> {
        localDataSource.deleteReminder(reminderId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteReminder(reminderId)
        }.await()
        if (remoteResult.code() == SUCCESS_CODE) {
            Log.i("Reminder Repository", "successfully deleting reminder: $remoteResult")
        } else {
            localDataSource.upsertDeletedReminderId(DeletedReminderIdEntity(reminderId))
            Log.e("Reminder Repository", "error deleting reminder: $remoteResult")
        }
        return kotlin.Result.success(Unit)
    }
}
