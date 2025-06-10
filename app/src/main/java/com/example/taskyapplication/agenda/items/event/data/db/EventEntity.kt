package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    @ColumnInfo(name = "remind_at") val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean
)

@Entity(
    tableName = "event_attendees",
    foreignKeys = [ForeignKey(
        entity = EventEntity::class,
        parentColumns = ["id"],
        childColumns = ["eventId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class EventAttendeeEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val eventId: String,
    val email: String,
    val fullName: String,
    val userId: String,
    val isGoing: Boolean,
    val remindAt: Long
)

@Entity(
    tableName = "event_photos",
    foreignKeys = [ForeignKey(
        entity = EventEntity::class,
        parentColumns = ["id"],
        childColumns = ["key"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class EventPhotosEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val key: String,
    val url: String
)