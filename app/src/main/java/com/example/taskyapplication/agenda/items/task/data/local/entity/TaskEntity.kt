package com.example.taskyapplication.agenda.items.task.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val time: LocalTime,
    val date: LocalDate,
    @ColumnInfo(name = "remind_at") val remindAt: Long,
    val isDone: Boolean = false
)

@Entity(tableName = "deleted_task_ids")
data class DeletedTaskIdEntity(
    @PrimaryKey
    val id: String
)
