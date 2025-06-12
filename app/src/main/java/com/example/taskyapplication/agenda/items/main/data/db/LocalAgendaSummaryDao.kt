package com.example.taskyapplication.agenda.items.main.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LocalAgendaSummaryDao {

    @Query("SELECT * FROM task_summary")
    suspend fun getTaskSummaries(): List<TaskSummaryEntity>

    @Query("SELECT * FROM event_summary")
    suspend fun getEventSummaries(): List<EventSummaryEntity>

    @Query("SELECT * FROM reminder_summary")
    suspend fun getReminderSummaries(): List<ReminderSummaryEntity>

    @Upsert
    suspend fun upsertTaskSummary(taskSummary: TaskSummaryEntity)

    @Upsert
    suspend fun upsertEventSummary(eventSummary: EventSummaryEntity)

    @Upsert
    suspend fun upsertReminderSummary(reminderSummary: ReminderSummaryEntity)
}