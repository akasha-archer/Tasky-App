package com.example.taskyapplication.agenda.task.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskyapplication.agenda.task.domain.model.TaskExternalModel

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

fun TaskEntity.asTask() = TaskExternalModel(
    id = id,
    title = title,
    description = description,
    time = time,
    remindAt = remindAt,
    isDone = isDone
)