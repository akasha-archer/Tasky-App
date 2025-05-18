package com.example.taskyapplication.agenda.task.data.local.dao

import androidx.room.Database
import com.example.taskyapplication.agenda.task.data.local.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase {
    abstract fun taskDao(): TaskDao
}
