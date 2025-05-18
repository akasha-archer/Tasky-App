package com.example.taskyapplication.agenda.task.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    @ColumnInfo(name = "remind_at") val remindAt: Long,
    val isDone: Boolean
)
