package com.example.taskyapplication.agenda.items.event.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventDao
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EventLocalDataSource {
    fun getEvents(): Flow<List<EventEntity>>
    suspend fun createEvent(event: EventEntity): Result<Unit, DataError.Local>
    suspend fun updateEvent(event: EventEntity): Result<Unit, DataError.Local>
    suspend fun upsertAllEvents(events: List<EventEntity>): Result<Unit, DataError.Local>
    suspend fun getEvent(eventId: String): EventEntity
    suspend fun deleteEvent(eventId: String)
    suspend fun deleteAllEvents()
    suspend fun upsertAttendee(attendee: AttendeeEntity)
    suspend fun getAttendeesForEvent(eventId: String): List<AttendeeEntity>
}

class EventLocalDataSourceImpl @Inject constructor(
    private val eventDao: EventDao
) : EventLocalDataSource {

    override fun getEvents(): Flow<List<EventEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun createEvent(event: EventEntity): Result<Unit, DataError.Local> {
        return try {
            eventDao.upsertEvent(event)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun updateEvent(event: EventEntity): Result<Unit, DataError.Local> {
        return try {
            eventDao.upsertEvent(event)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertAllEvents(events: List<EventEntity>): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun getEvent(eventId: String): EventEntity {
        return eventDao.getEventById(eventId)
    }

    override suspend fun deleteEvent(eventId: String) {
        return eventDao.deleteEventById(eventId)
    }

    override suspend fun deleteAllEvents() {
        TODO("Not yet implemented")
    }

    override suspend fun upsertAttendee(attendee: AttendeeEntity) {
        eventDao.upsertAttendee(attendee)
    }

    override suspend fun getAttendeesForEvent(eventId: String): List<AttendeeEntity> {
        return eventDao.getAttendees()
    }
}
