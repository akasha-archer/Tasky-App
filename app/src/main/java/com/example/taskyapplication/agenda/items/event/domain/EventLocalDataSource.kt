package com.example.taskyapplication.agenda.items.event.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventDao
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventPhotoEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventWithPhotos
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

interface EventLocalDataSource {
    suspend fun getEvents(): List<EventWithPhotos>
    fun getEventsForSelectedDate(date: LocalDate): Flow<List<EventEntity>>
    suspend fun createEvent(
        event: EventEntity,
        photos: List<EventPhotoEntity>
    ): Result<Unit, DataError.Local>

    suspend fun updateEvent(
        event: EventEntity,
        photos: List<EventPhotoEntity>
    ): Result<Unit, DataError.Local>

    suspend fun insertEventWithoutPhotos(event: EventEntity): Result<Unit, DataError.Local>
    suspend fun upsertAllEvents(events: List<EventEntity>): Result<Unit, DataError.Local>
    suspend fun getEventWithoutPhotos(eventId: String): EventEntity
    suspend fun deleteEvent(eventId: String)
    suspend fun deleteAllEvents()
    suspend fun upsertAttendee(attendee: AttendeeEntity)
    suspend fun getAttendeesForEvent(eventId: String): List<AttendeeEntity>
    suspend fun upsertPhotos(photos: List<EventPhotoEntity>)
    suspend fun getPhotosForEvent(eventId: String): List<EventPhotoEntity>
    suspend fun deletePhotosByEventId(eventId: String)
}

class EventLocalDataSourceImpl @Inject constructor(
    private val eventDao: EventDao
) : EventLocalDataSource {

    override suspend fun getEvents(): List<EventWithPhotos> {
        return eventDao.getAllEventsWithPhotos()
    }

    override fun getEventsForSelectedDate(date: LocalDate): Flow<List<EventEntity>> {
        return eventDao.getAllEventsForSelectedDate(date)
    }

    override suspend fun createEvent(
        event: EventEntity,
        photos: List<EventPhotoEntity>
    ): Result<Unit, DataError.Local> {
        return try {
            eventDao.insertEventWithPhotos(event, photos)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun updateEvent(
        event: EventEntity,
        photos: List<EventPhotoEntity>
    ): Result<Unit, DataError.Local> {
        return try {
            eventDao.updateEventWithPhotos(event, photos)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun insertEventWithoutPhotos(event: EventEntity): Result<Unit, DataError.Local> {
        return try {
            eventDao.upsertEvent(event)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertAllEvents(events: List<EventEntity>): Result<Unit, DataError.Local> {
        return try {
            events.forEach {
                eventDao.upsertEvent(it)
            }
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertPhotos(photos: List<EventPhotoEntity>) {
        try {
            eventDao.upsertEventPhotos(photos)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getEventWithoutPhotos(eventId: String): EventEntity {
        return eventDao.getEventById(eventId)
    }

    override suspend fun getPhotosForEvent(eventId: String): List<EventPhotoEntity> {
        return eventDao.getPhotosForEvent(eventId)
    }

    override suspend fun deleteEvent(eventId: String) {
        return eventDao.deleteEventById(eventId)
    }

    override suspend fun deletePhotosByEventId(eventId: String) {
        eventDao.deletePhotosForEvent(eventId)
    }

    override suspend fun deleteAllEvents() {
        eventDao.deleteAllEvents()
    }

    override suspend fun upsertAttendee(attendee: AttendeeEntity) {
        eventDao.upsertAttendee(attendee)
    }

    override suspend fun getAttendeesForEvent(eventId: String): List<AttendeeEntity> {
        return eventDao.getAttendees()
    }
}
