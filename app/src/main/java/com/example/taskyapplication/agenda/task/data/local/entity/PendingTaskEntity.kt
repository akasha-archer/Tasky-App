package com.example.taskyapplication.agenda.task.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskyapplication.agenda.task.data.mappers.asTaskEntity
import com.example.taskyapplication.agenda.task.data.network.models.TaskNetworkModel
import com.example.taskyapplication.agenda.task.data.network.models.UpdateTaskBody

@Entity(tableName = "pendingtasks")
data class PendingTaskEntity(
    @Embedded val task: TaskEntity,
    @PrimaryKey(autoGenerate = false)
    val taskId: String = task.id
)

fun UpdateTaskBody.asPendingTaskEntity() = PendingTaskEntity(
    task = this.asTaskEntity()
)

fun TaskNetworkModel.asPendingTaskEntity() = PendingTaskEntity(
    task = this.asTaskEntity()
)