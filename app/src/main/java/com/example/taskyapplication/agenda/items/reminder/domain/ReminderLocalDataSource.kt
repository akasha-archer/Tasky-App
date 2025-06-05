package com.example.taskyapplication.agenda.items.reminder.domain

import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderDao
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ReminderLocalDataSource {
    fun getReminders(): Flow<List<ReminderDto>>
    suspend fun upsertReminder(reminder: ReminderDto): Result<Unit, DataError.Local>
    suspend fun upsertAllReminders(tasks: List<ReminderDto>): Result<Unit, DataError.Local>
    suspend fun getReminder(reminderId: String): ReminderDto
    suspend fun deleteReminder(reminderId: String)
    suspend fun deleteAllReminders()
}

class ReminderLocalDataSourceImpl(
    reminderDao: ReminderDao
): ReminderLocalDataSource {

    override fun getReminders(): Flow<List<ReminderDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertReminder(reminder: ReminderDto): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertAllReminders(tasks: List<ReminderDto>): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun getReminder(reminderId: String): ReminderDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReminder(reminderId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllReminders() {
        TODO("Not yet implemented")
    }
}
