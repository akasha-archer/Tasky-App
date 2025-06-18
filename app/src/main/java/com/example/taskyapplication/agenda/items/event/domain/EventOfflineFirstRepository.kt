package com.example.taskyapplication.agenda.items.event.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.EventLocalDataSource
import com.example.taskyapplication.agenda.items.event.data.EventRemoteDataSource
import com.example.taskyapplication.agenda.items.event.data.GetAttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.UpdatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.event.data.db.asAttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.toEventEntity
import com.example.taskyapplication.agenda.items.event.data.toPhotoEntities
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.asEmptyDataResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import okhttp3.MultipartBody
import javax.inject.Inject

class EventOfflineFirstRepository @Inject constructor(
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource,
    private val imageMultiPartProvider: ImageMultiPartProvider,
    private val applicationScope: CoroutineScope,
    @ApplicationContext private val applicationContext: Context,
    ) : EventRepository {

    override suspend fun createMultiPartImages(
        userPhots: List<Uri>
    ): List<MultipartBody.Part> {
        return imageMultiPartProvider.createMultipartParts(applicationContext, userPhots)
    }

    override suspend fun validateAttendee(
        email: String
    ): Result<GetAttendeeResponse, DataError> {
        val remoteResult = remoteDataSource.verifyAttendee(email)
        return when (remoteResult) {
            is Result.Error -> {
                Log.e("Event Repository", "error validating attendee")
                Result.Error(remoteResult.error)
            }

            is Result.Success -> {
                if (remoteResult.data.doesUserExist) {
                    val attendee = remoteResult.data.attendee
                    localDataSource.upsertAttendee(attendee.asAttendeeEntity())
                }
                Result.Success(remoteResult.data)
            }
        }
    }

    override suspend fun getAttendeeListForEvent(eventId: String): List<AttendeeEntity> {
        return localDataSource.getAttendeesForEvent(eventId)
    }

    override suspend fun createNewEvent(
        createEventNetworkModel: CreateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<CreatedEventResponse, DataError> {
        val localResult = localDataSource.insertEventWithoutPhotos(
            createEventNetworkModel.toEventEntity(),
        )
        if (localResult !is Result.Success) {
            Log.e("Error inserting new event", "error: $localResult")
        }
        val remoteResult = remoteDataSource.createEvent(
            createEventNetworkModel,
            photos
        )

        return when (remoteResult) {
            is Result.Error -> {
                Result.Error(remoteResult.error)
            }
            is Result.Success -> {
                localDataSource.insertEventWithoutPhotos(
                    remoteResult.data.toEventEntity()
                )
                localDataSource.upsertPhotos(
                    remoteResult.data.toPhotoEntities()
                )
                Result.Success(remoteResult.data)
            }
        }
    }

    override suspend fun updateEvent(
        updateEventNetworkModel: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): Result<UpdatedEventResponse, DataError> {
        val localResult = localDataSource.insertEventWithoutPhotos(
            updateEventNetworkModel.toEventEntity(),
        )
        if (localResult !is Result.Success) {
            Log.e("Error inserting new event", "error: $localResult")
        }
        val remoteResult = remoteDataSource.updateEvent(
            updateEventNetworkModel,
            photos
        )

        return when (remoteResult) {
            is Result.Error -> {
                Result.Error(remoteResult.error)
            }
            is Result.Success -> {
                localDataSource.upsertPhotos(
                    remoteResult.data.toPhotoEntities()
                )
                Result.Success(remoteResult.data)
            }
        }
    }

    override suspend fun getEventWithoutImages(eventId: String): EventEntity {
        return localDataSource.getEventWithoutPhotos(eventId)
    }

    override suspend fun deleteEvent(eventId: String): EmptyResult<DataError> {
        localDataSource.deleteEvent(eventId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteEvent(eventId)
        }.await()

        if (remoteResult !is Result.Success) {
           Log.e("Event Repository", "error deleting event: $remoteResult")
        } else {
            Log.e("Event Repository", "Successfully deleted event: $remoteResult")
        }
        return remoteResult.asEmptyDataResult()
    }

}
