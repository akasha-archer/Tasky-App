package com.example.taskyapplication.agenda.items.reminder.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ReminderDao {

    @Upsert
    suspend fun upsertDeletedReminderId(deletedReminderId: DeletedReminderIdEntity)

    @Query("SELECT * FROM deleted_reminder_ids")
    suspend fun getDeletedReminderIds(): List<DeletedReminderIdEntity>

    @Query("SELECT * FROM reminders")
    suspend fun getAllReminders(): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE date = :date ORDER BY time ASC")
    fun getAllRemindersForSelectedDate(date: LocalDate): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    suspend fun getReminderById(reminderId: String): ReminderEntity?

    @Query("DELETE FROM reminders WHERE id = :reminderId")
    suspend fun deleteReminderById(reminderId: String)

    @Upsert
    suspend fun upsertReminder(reminderEntity: ReminderEntity)

    @Upsert
    suspend fun upsertAllReminders(reminders: List<ReminderEntity>)

    @Query("DELETE FROM reminders")
    suspend fun deleteAllReminders() // to delete all tasks when user logs out
}
