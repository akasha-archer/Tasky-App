package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface EventDao {

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