package com.example.taskyapplication.agenda.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskyapplication.agenda.items.event.data.db.DbTypeConverters
import com.example.taskyapplication.agenda.items.event.data.db.EventDao
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.main.data.db.EventSummaryEntity
import com.example.taskyapplication.agenda.items.main.data.db.LocalAgendaSummaryDao
import com.example.taskyapplication.agenda.items.main.data.db.ReminderSummaryEntity
import com.example.taskyapplication.agenda.items.main.data.db.TaskSummaryEntity
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderDao
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.task.data.local.dao.PendingTaskDao
import com.example.taskyapplication.agenda.items.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.items.task.data.local.entity.PendingTaskEntity
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity

// Single database where tables for all 3 agenda item types will be added
@Database(
    entities = [
        TaskEntity::class,
        PendingTaskEntity::class,
        ReminderEntity::class,
        EventEntity::class,
        TaskSummaryEntity::class,
        EventSummaryEntity::class,
        ReminderSummaryEntity::class
    ], version = 1
)
@TypeConverters(DbTypeConverters::class)
abstract class AgendaDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun pendingTaskDao(): PendingTaskDao
    abstract fun reminderDao(): ReminderDao
    abstract fun eventDao(): EventDao
    abstract fun localAgendaSummaryDao(): LocalAgendaSummaryDao
}
