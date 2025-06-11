package com.example.taskyapplication.agenda.items.main.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.main.data.LocalAgendaSummary
import com.example.taskyapplication.agenda.items.main.data.db.EventSummaryEntity
import com.example.taskyapplication.agenda.items.main.data.db.LocalAgendaSummaryDao
import com.example.taskyapplication.agenda.items.main.data.db.ReminderSummaryEntity
import com.example.taskyapplication.agenda.items.main.data.db.TaskSummaryEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import javax.inject.Inject

interface AgendaLocalDataSource {
    suspend fun getAgendaForDate(
        date: Long
    ): LocalAgendaSummary

    suspend fun upsertTaskSummary(task: TaskSummaryEntity): Result<Unit, DataError.Local>
    suspend fun upsertEventSummary(event: EventSummaryEntity): Result<Unit, DataError.Local>
    suspend fun upsertReminderSummary(reminder: ReminderSummaryEntity): Result<Unit, DataError.Local>
}

class AgendaItemsLocalDataSource @Inject constructor(
    private val localAgendaSummaryDao: LocalAgendaSummaryDao
) : AgendaLocalDataSource {

    override suspend fun getAgendaForDate(
        date: Long
    ): LocalAgendaSummary {
        val tasks = localAgendaSummaryDao.getTaskSummaries()
        val events = localAgendaSummaryDao.getEventSummaries()
        val reminders = localAgendaSummaryDao.getReminderSummaries()

        return LocalAgendaSummary(
            tasks = tasks,
            events = events,
            reminders = reminders
        )
    }

    override suspend fun upsertTaskSummary(
        task: TaskSummaryEntity
    ): Result<Unit, DataError.Local> {
        return try {
            localAgendaSummaryDao.upsertTaskSummary(task)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertEventSummary(event: EventSummaryEntity): Result<Unit, DataError.Local> {
        return try {
            localAgendaSummaryDao.upsertEventSummary(event)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertReminderSummary(reminder: ReminderSummaryEntity): Result<Unit, DataError.Local> {
        return try {
            localAgendaSummaryDao.upsertReminderSummary(reminder)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}