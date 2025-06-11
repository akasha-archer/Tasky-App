package com.example.taskyapplication.agenda.items.main.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType

@Entity(tableName =  "task_summary")
data class TaskSummaryEntity(
    @PrimaryKey
    val id: String,
    val description: String,
    val title: String,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    val type: AgendaItemType = AgendaItemType.TASK,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false
)

@Entity(tableName = "event_summary")
data class EventSummaryEntity(
    @PrimaryKey
    val id: String,
    val description: String,
    val title: String,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    val type: AgendaItemType = AgendaItemType.EVENT,
    @ColumnInfo(name = "is_attendee") val isAttendee: Boolean,
)

@Entity(tableName = "reminder_summary")
data class ReminderSummaryEntity(
    @PrimaryKey
    val id: String,
    val description: String,
    val title: String,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    val type: AgendaItemType = AgendaItemType.REMINDER,
)