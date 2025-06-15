package com.example.taskyapplication.agenda.items.event.domain

import android.database.sqlite.SQLiteFullException
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.db.DeletedEventIdEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventDao
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventPhotoEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventWithPhotos
import com.example.taskyapplication.agenda.items.task.data.local.entity.DeletedTaskIdEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

interface EventLocalDataSource {
    suspend fun upsertDeletedEventId(deletedEventId: DeletedEventIdEntity): kotlin.Result<Unit>
    suspend fun getDeletedEventIds(): List<DeletedEventIdEntity>
    suspend fun getEvents(): List<EventWithPhotos>
    suspend fun getEventsWithoutPhotos(): List<EventEntity>
    fun getEventsForSelectedDate(date: LocalDate): Flow<List<EventEntity>>
    suspend fun createEvent(
        event: EventEntity,
        photos: List<EventPhotoEntity>
    ): Result<Unit, DataError.Local>

    suspend fun updateEvent(
        event: EventEntity,
        photos: List<EventPhotoEntity>
    ): Result<Unit, DataError.Local>

    suspend fun insertEventWithoutPhotos(event: EventEntity): kotlin.Result<Unit>
    suspend fun upsertAllEvents(events: List<EventEntity>): Result<Unit, DataError.Local>
    suspend fun getEventWithoutPhotos(eventId: String): EventEntity
    suspend fun deleteEvent(eventId: String)
    suspend fun deleteAllEvents()
    suspend fun upsertAttendee(attendee: AttendeeEntity)
    suspend fun getAttendeesForEvent(eventId: String): List<AttendeeEntity>
    suspend fun upsertPhotos(photos: List<EventPhotoEntity>)
    suspend fun getPhotosForEvent(eventId: String): List<EventPhotoEntity>
    suspend fun deletePhotosByEventId(eventId: String)
    suspend fun getEventById(eventId: String): EventEntity?

}

class EventLocalDataSourceImpl @Inject constructor(
    private val eventDao: EventDao
) : EventLocalDataSource {

    override suspend fun getEventById(eventId: String): EventEntity? {
        return eventDao.getEventById(eventId)
    }

    override suspend fun upsertDeletedEventId(deletedEventId: DeletedEventIdEntity): kotlin.Result<Unit> {
            return try {
                eventDao.upsertDeletedEventId(deletedEventId)
                kotlin.Result.success(Unit)
            } catch (e: SQLiteFullException) {
                kotlin.Result.failure(e)
            }
    }

    override suspend fun getDeletedEventIds(): List<DeletedEventIdEntity> {
        return eventDao.getDeletedEventIds()
    }

    override suspend fun getEvents(): List<EventWithPhotos> {
        return eventDao.getAllEventsWithPhotos()
    }

    override suspend fun getEventsWithoutPhotos(): List<EventEntity> {
        return eventDao.getAllEventsWithoutPhotos()
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

    override suspend fun insertEventWithoutPhotos(event: EventEntity): kotlin.Result<Unit> {
        return try {
            eventDao.upsertEvent(event)
            kotlin.Result.success(Unit)
        } catch (e: SQLiteFullException) {
            kotlin.Result.failure(e)
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
