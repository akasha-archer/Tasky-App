package com.example.taskyapplication.agenda.items.reminder.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderDao
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ReminderLocalDataSource {
    fun getReminders(): Flow<List<ReminderDto>>
    suspend fun upsertReminder(reminder: ReminderEntity): Result<Unit, DataError.Local>
    suspend fun upsertAllReminders(tasks: List<ReminderEntity>): Result<Unit, DataError.Local>
    suspend fun getReminder(reminderId: String): ReminderEntity
    suspend fun deleteReminder(reminderId: String)
    suspend fun deleteAllReminders()
}

class ReminderLocalDataSourceImpl @Inject constructor(
   private val reminderDao: ReminderDao
): ReminderLocalDataSource {

    override fun getReminders(): Flow<List<ReminderDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertReminder(reminder: ReminderEntity): Result<Unit, DataError.Local> {
        return try {
            reminderDao.upsertReminder(reminder)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertAllReminders(tasks: List<ReminderEntity>): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun getReminder(reminderId: String): ReminderEntity {
        return reminderDao.getReminderById(reminderId)
    }

    override suspend fun deleteReminder(reminderId: String) {
        reminderDao.deleteReminderById(reminderId)
    }

    override suspend fun deleteAllReminders() {
        TODO("Not yet implemented")
    }
}
