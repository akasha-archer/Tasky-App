package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.Query
import androidx.room.Upsert

interface EventDao {

    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): EventEntity

    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEventById(eventId: String)

    @Upsert
    suspend fun upsertEvent(eventEntity: EventEntity)

    @Upsert
    suspend fun upsertAllEvents(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents() // to delete all tasks when user logs out
}