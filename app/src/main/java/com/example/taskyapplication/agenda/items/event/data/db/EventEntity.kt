package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val startTime: LocalTime,
    val endDate: LocalDate,
    val endTime: LocalTime,
    @ColumnInfo(name = "remind_at") val remindAt: Long,
    val photos: List<String>, //photo urls
    val photoKeys: List<String>, // id for photos
    val attendeeIds: List<String>, // ids for attendees
    val host: String,
    val isUserEventCreator: Boolean
)
