package com.example.taskyapplication.agenda.items.event.domain

import com.example.taskyapplication.agenda.items.event.data.EventDto
import com.example.taskyapplication.agenda.items.event.data.db.EventDao
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EventLocalDataSource {
    fun getEvents(): Flow<List<EventDto>>
    suspend fun upsertEvent(reminder: EventDto): Result<Unit, DataError.Local>
    suspend fun upsertAllEvents(tasks: List<EventDto>): Result<Unit, DataError.Local>
    suspend fun getEvent(reminderId: String): EventDto
    suspend fun deleteEvent(reminderId: String)
    suspend fun deleteAllEvents()
}

class EventLocalDataSourceImpl @Inject constructor(
    private val eventDao: EventDao
) : EventLocalDataSource {

    override fun getEvents(): Flow<List<EventDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertEvent(reminder: EventDto): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertAllEvents(tasks: List<EventDto>): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun getEvent(reminderId: String): EventDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvent(reminderId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllEvents() {
        TODO("Not yet implemented")
    }

}