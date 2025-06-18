package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EventDao {

    @Upsert
    suspend fun upsertDeletedEventId(deletedEventId: DeletedEventIdEntity)

    @Query("SELECT * FROM deleted_event_ids")
    suspend fun getDeletedEventIds(): List<DeletedEventIdEntity>

    @Upsert
    suspend fun upsertEventPhotos(photos: List<EventPhotoEntity>)

    @Query("SELECT * FROM event_photos WHERE eventId = :eventId")
    suspend fun getPhotosForEvent(eventId: String): List<EventPhotoEntity>

    @Query("DELETE FROM event_photos WHERE eventId = :eventId")
    suspend fun deletePhotosForEvent(eventId: String)

    // Example of how to save an event with its photos in a transaction
    @Transaction
    suspend fun insertEventWithPhotos(event: EventEntity, photos: List<EventPhotoEntity>) {
        upsertEvent(event) // Assuming you have an insertEvent(event: EventEntity) method
        if (photos.isNotEmpty()) {
            upsertEventPhotos(photos)
        }
    }

    // Example of how to update an event with its photos (delete old, insert new)
    @Transaction
    suspend fun updateEventWithPhotos(event: EventEntity, photos: List<EventPhotoEntity>) {
        upsertEvent(event) // Assuming you have an upsertEvent(event: EventEntity) method
        deletePhotosForEvent(event.id) // Delete existing photos for this event
        if (photos.isNotEmpty()) {
            upsertEventPhotos(photos) // Insert the new list of photos
        }
    }

    @Transaction // Important for @Relation queries
    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventWithPhotos(eventId: String): EventWithPhotos?

    @Transaction // Important for @Relation queries
    @Query("SELECT * FROM events")
    suspend fun getAllEventsWithPhotos(): List<EventWithPhotos>

    @Query("SELECT * FROM events")
    suspend fun getAllEventsWithoutPhotos(): List<EventEntity>
    @Transaction // Important for @Relation queries
    @Query("SELECT * FROM events WHERE startDate = :date ORDER BY startTime ASC")
     fun getAllEventsForSelectedDate(date: LocalDate): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): EventEntity

    @Query("SELECT * FROM attendees")
    suspend fun getAttendees(): List<AttendeeEntity> //eventId: String

    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEventById(eventId: String)

    @Upsert
    suspend fun upsertEvent(eventEntity: EventEntity)

    @Upsert
    suspend fun upsertAttendee(attendeeEntity: AttendeeEntity)

    @Upsert
    suspend fun upsertAllEvents(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents() // to delete all tasks when user logs out
}