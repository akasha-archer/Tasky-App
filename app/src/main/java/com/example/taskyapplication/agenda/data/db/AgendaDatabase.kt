package com.example.taskyapplication.agenda.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.db.DbTypeConverters
import com.example.taskyapplication.agenda.items.event.data.db.DeletedEventIdEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventDao
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventPhotoEntity
import com.example.taskyapplication.agenda.items.reminder.data.db.DeletedReminderIdEntity
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderDao
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.items.task.data.local.entity.DeletedTaskIdEntity
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity

// Single database where tables for all 3 agenda item types will be added
@Database(
    entities = [
        TaskEntity::class,
        ReminderEntity::class,
        EventEntity::class,
        EventPhotoEntity::class,
        AttendeeEntity::class,
        DeletedTaskIdEntity::class,
        DeletedEventIdEntity::class,
        DeletedReminderIdEntity::class
    ], version = 1
)
@TypeConverters(DbTypeConverters::class)
abstract class AgendaDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun reminderDao(): ReminderDao
    abstract fun eventDao(): EventDao
}
