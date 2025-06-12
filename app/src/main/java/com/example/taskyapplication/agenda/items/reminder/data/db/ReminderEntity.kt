package com.example.taskyapplication.agenda.items.reminder.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val date: String,
    @ColumnInfo(name = "remind_at") val remindAt: Long
)
