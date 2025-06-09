package com.example.taskyapplication.agenda.items.event.domain

import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.UpdatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import okhttp3.MultipartBody
import javax.inject.Inject

class EventOfflineFirstRepository @Inject constructor(
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource,
    private val applicationScope: CoroutineScope
): EventRepository {

    override suspend fun createNewEvent(
        createEventNetworkModel: CreateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<CreatedEventResponse, DataError> {
        val remoteResult = remoteDataSource.createEvent(
            createEventNetworkModel,
            photos
        )
        return when (remoteResult) {
            is Result.Error -> {
                TODO("insert to pending table")
            }
            is Result.Success -> {
                TODO("insert to local db")
//                applicationScope.async {
//                    localDataSource.upsertReminder(request.toReminderEntity())
//                }.await()
            }
        }
    }

    override suspend fun updateEvent(
        updateEventNetworkModel: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<UpdatedEventResponse, DataError> {
        val remoteResult = remoteDataSource.updateEvent(
            updateEventNetworkModel,
            photos
        )
        return when (remoteResult) {
            is Result.Error -> {
                TODO("insert to pending table")
            }
            is Result.Success -> {
                TODO("insert to local db")
            }
        }
    }

    override suspend fun getEventById(eventId: String): EventEntity {
        return localDataSource.getEvent(eventId)
    }

    override suspend fun deleteEvent(eventId: String): EmptyResult<DataError> {
        localDataSource.deleteEvent(eventId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteEvent(eventId)
        }.await()

        if (remoteResult !is Result.Success) {
            TODO("insert to pending table")
        }
        return remoteResult.asEmptyDataResult()
    }
}
