package com.example.taskyapplication.agenda.items.reminder.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val time: LocalTime,
    val date: LocalDate,
    @ColumnInfo(name = "remind_at") val remindAt: Long
)

@Entity(tableName = "deleted_reminder_ids")
data class DeletedReminderIdEntity(
    @PrimaryKey
    val id: String
)