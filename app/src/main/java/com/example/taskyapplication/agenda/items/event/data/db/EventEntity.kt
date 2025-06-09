package com.example.taskyapplication.agenda.items.event.data.db

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    @ColumnInfo(name = "remind_at") val remindAt: String,
    val attendeeIds: List<String>,
    val photos: List<Uri>
)
