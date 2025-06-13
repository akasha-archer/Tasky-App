package com.example.taskyapplication.agenda.items.reminder.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderDao
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderResponse
import com.example.taskyapplication.agenda.items.reminder.data.models.toReminderEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

interface ReminderLocalDataSource {
    fun getRemindersForDate(date: LocalDate): Flow<List<ReminderEntity>>
    suspend fun upsertReminder(reminder: ReminderEntity): Result<Unit, DataError.Local>
    suspend fun upsertAllReminders(reminders: List<ReminderResponse>): Result<Unit, DataError.Local>
    suspend fun getReminder(reminderId: String): ReminderEntity
    suspend fun deleteReminder(reminderId: String)
    suspend fun deleteAllReminders()
}

class ReminderLocalDataSourceImpl @Inject constructor(
   private val reminderDao: ReminderDao
): ReminderLocalDataSource {

    override fun getRemindersForDate(date: LocalDate): Flow<List<ReminderEntity>> {
        return reminderDao.getAllRemindersForSelectedDate(date)
    }

    override suspend fun upsertReminder(reminder: ReminderEntity): Result<Unit, DataError.Local> {
        return try {
            reminderDao.upsertReminder(reminder)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertAllReminders(reminders: List<ReminderResponse>): Result<Unit, DataError.Local> {
        return try {
            reminders.forEach {
                reminderDao.upsertReminder(it.toReminderEntity())
            }
            Result.Success(Unit)
        } catch(e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getReminder(reminderId: String): ReminderEntity {
        return reminderDao.getReminderById(reminderId)
    }

    override suspend fun deleteReminder(reminderId: String) {
        reminderDao.deleteReminderById(reminderId)
    }

    override suspend fun deleteAllReminders() {
        reminderDao.deleteAllReminders()
    }
}
