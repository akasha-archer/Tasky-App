package com.example.taskyapplication.agenda.items.reminder.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.reminder.data.db.DeletedReminderIdEntity
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderDao
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderResponse
import com.example.taskyapplication.agenda.items.reminder.data.models.toReminderEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

interface ReminderLocalDataSource {

    suspend fun upsertDeletedReminderId(deletedReminderId: DeletedReminderIdEntity): kotlin.Result<Unit>

    suspend fun getDeletedReminderIds(): List<DeletedReminderIdEntity>
    suspend fun getAllReminders(): List<ReminderEntity>
    fun getRemindersForDate(date: LocalDate): Flow<List<ReminderEntity>>
    suspend fun upsertReminder(reminder: ReminderEntity): kotlin.Result<Unit>
    suspend fun upsertAllReminders(reminders: List<ReminderResponse>): kotlin.Result<Unit>
    suspend fun getReminder(reminderId: String): ReminderEntity?
    suspend fun deleteReminder(reminderId: String)
    suspend fun deleteAllReminders()
}

class ReminderLocalDataSourceImpl @Inject constructor(
   private val reminderDao: ReminderDao
): ReminderLocalDataSource {
    override suspend fun upsertDeletedReminderId(deletedReminderId: DeletedReminderIdEntity): Result<Unit> {
        return try {
            reminderDao.upsertDeletedReminderId(deletedReminderId)
            kotlin.Result.success(Unit)
        } catch (e: SQLiteFullException) {
            kotlin.Result.failure(e)
        }
    }

    override suspend fun getDeletedReminderIds(): List<DeletedReminderIdEntity> {
        return reminderDao.getDeletedReminderIds()
    }

    override suspend fun getAllReminders(): List<ReminderEntity> {
        return reminderDao.getAllReminders()
    }

    override fun getRemindersForDate(date: LocalDate): Flow<List<ReminderEntity>> {
        return reminderDao.getAllRemindersForSelectedDate(date)
    }

    override suspend fun upsertReminder(reminder: ReminderEntity): kotlin.Result<Unit> {
        return try {
            reminderDao.upsertReminder(reminder)
            kotlin.Result.success(Unit)
        } catch (e: SQLiteFullException) {
            kotlin.Result.failure(e)
        }
    }

    override suspend fun upsertAllReminders(reminders: List<ReminderResponse>): kotlin.Result<Unit> {
        return try {
            reminders.forEach {
                reminderDao.upsertReminder(it.toReminderEntity())
            }
            kotlin.Result.success(Unit)
        } catch(e: SQLiteFullException) {
            kotlin.Result.failure(e)
        }
    }

    override suspend fun getReminder(reminderId: String): ReminderEntity? {
        return reminderDao.getReminderById(reminderId)
    }

    override suspend fun deleteReminder(reminderId: String) {
        reminderDao.deleteReminderById(reminderId)
    }

    override suspend fun deleteAllReminders() {
        reminderDao.deleteAllReminders()
    }
}
