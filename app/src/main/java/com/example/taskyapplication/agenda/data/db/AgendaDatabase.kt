package com.example.taskyapplication.agenda.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskyapplication.agenda.task.data.local.dao.PendingTaskDao
import com.example.taskyapplication.agenda.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.task.data.local.entity.PendingTaskEntity
import com.example.taskyapplication.agenda.task.data.local.entity.TaskEntity

// Single database where tables for all 3 agenda item types will be added
@Database(entities = [
    TaskEntity::class,
    PendingTaskEntity::class,
], version = 1)
abstract class AgendaDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun pendingTaskDao(): PendingTaskDao
}
