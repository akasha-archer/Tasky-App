package com.example.taskyapplication.agenda.reminder.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminders")
    suspend fun getAllTReminders(): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    suspend fun getReminderById(reminderId: String): ReminderEntity

    @Query("DELETE FROM reminders WHERE id = :reminderId")
    suspend fun deleteReminderById(reminderId: String)

    @Upsert
    suspend fun upsertReminder(reminderEntity: ReminderEntity)

    @Upsert
    suspend fun upsertAllReminders(reminders: List<ReminderEntity>)

    @Query("DELETE FROM reminders")
    suspend fun deleteAllReminders() // to delete all tasks when user logs out
}
