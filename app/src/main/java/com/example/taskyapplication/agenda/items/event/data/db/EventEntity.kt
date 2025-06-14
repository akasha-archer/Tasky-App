package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
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

// Event Photo entity
@Entity(
    tableName = "event_photos",
    // Define a composite primary key using the event's ID and the photo's unique key
    primaryKeys = ["eventId", "photoKey"],
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = ["id"], // 'id' column in EventEntity
            childColumns = ["eventId"], // 'eventId' column in this EventPhotoEntity
            onDelete = ForeignKey.CASCADE // If an EventEntity is deleted, its photos are also deleted
        )
    ],
    // Add an index on eventId for faster querying of photos for a specific event
    indices = [Index(value = ["eventId"])]
)
data class EventPhotoEntity(
    val eventId: String,    // Foreign key linking to EventEntity and part of composite PK
    val photoKey: String,   // The 'key' from your EventPhotoResponse, part of composite PK
    val photoUrl: String    // The 'url' from your EventPhotoResponse
)

// POJO to hold an event and its photos
data class EventWithPhotos(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "id", // Primary key of EventEntity
        entityColumn = "eventId" // Foreign key in EventPhotoEntity
    )
    val photos: List<EventPhotoEntity>
)
