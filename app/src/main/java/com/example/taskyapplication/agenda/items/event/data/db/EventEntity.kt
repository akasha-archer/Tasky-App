package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val startDate: Long,
    val startTime: Long,
    val endDate: Long,
    val endTime: Long,
    @ColumnInfo(name = "remind_at") val remindAt: Long,
    val photos: List<String>, //photo urls
    val photoKeys: List<String>, // id for photos
    val attendeeIds: List<String>, // ids for attendees
    val host: String,
    val isUserEventCreator: Boolean
)
