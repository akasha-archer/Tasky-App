package com.example.taskyapplication.agenda.items.event.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.taskyapplication.agenda.items.event.data.CreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.GetAttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.UpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.db.AttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.db.DeletedEventIdEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.event.data.db.asAttendeeEntity
import com.example.taskyapplication.agenda.items.event.data.toEventEntity
import com.example.taskyapplication.agenda.items.event.data.toPhotoEntities
import com.example.taskyapplication.domain.utils.SUCCESS_CODE
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
    ): kotlin.Result<GetAttendeeResponse> {
        return try {
            val remoteResult = remoteDataSource.verifyAttendee(email)
            if (remoteResult.isSuccessful && remoteResult.body() != null) {
                 localDataSource.upsertAttendee(remoteResult.body()!!.attendee.asAttendeeEntity())
                kotlin.Result.success(remoteResult.body()!!)
            } else {
                val errorMessage = remoteResult.errorBody()?.string() ?: "Attendee validation failed"
                Log.e("EventRepo", "Attendee validation failed: ${remoteResult.code()} - $errorMessage")
                kotlin.Result.failure(Exception("Attendee validation failed: ${remoteResult.code()} - $errorMessage"))
            }
        } catch (e: Exception) {
            Log.e("EventRepo", "Error validating attendee: ${e.message}", e)
            kotlin.Result.failure(e)
        }
    }

    override suspend fun getAttendeeListForEvent(eventId: String): List<AttendeeEntity> {
        return localDataSource.getAttendeesForEvent(eventId)
    }

    override suspend fun createNewEvent(
        request: CreateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): kotlin.Result<Unit> {
        val localResult = localDataSource.insertEventWithoutPhotos(
            request.toEventEntity(),
        )
        if (localResult != kotlin.Result.success(Unit)) {
            Log.e("Error inserting new event", "error: $localResult")
        } else {
            Log.i("Event Repository:", "Local Event created successfully")
        }

        return try {
            val remoteResult = remoteDataSource.createEvent(
                event = request,
                photos = photos
            )
            if (remoteResult.code() == SUCCESS_CODE) {
                localDataSource.insertEventWithoutPhotos(
                    remoteResult.body()?.toEventEntity() ?: request.toEventEntity()
                )
                localDataSource.upsertPhotos(
                    remoteResult.body()?.toPhotoEntities() ?: emptyList()
                )
                Log.i("Event Repository:", "Event created successfully")
            } else {
                Log.e(
                    "Event Repository: Error creating ${request.title} event",
                    remoteResult.message()
                )            }
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Event Repo:", e.message.toString())
            kotlin.Result.failure(e)
        }
    }

    override suspend fun updateEvent(
        request: UpdateEventNetworkModel,
        photos: List<MultipartBody.Part>
    ): kotlin.Result<Unit> {
        val localResult = localDataSource.insertEventWithoutPhotos(
            request.toEventEntity(),
        )
        if (localResult != kotlin.Result.success(Unit)) {
            Log.e("Error updating event", "error: $localResult")
        }
        return try {
            val remoteResult = remoteDataSource.updateEvent(
                event = request,
                photos = photos
            )
            if (remoteResult.code() == SUCCESS_CODE) {
                localDataSource.insertEventWithoutPhotos(
                    remoteResult.body()?.toEventEntity() ?: request.toEventEntity()
                )
                localDataSource.upsertPhotos(
                    remoteResult.body()?.toPhotoEntities() ?: emptyList()
                )
                Log.i("Event Repository:", "Event created successfully")
            } else {
                Log.e(
                    "Event Repository: Error creating ${request.title} event",
                    remoteResult.message()
                )            }
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Event Repo:", e.message.toString())
            kotlin.Result.failure(e)
        }
    }

    override suspend fun getEventWithoutImages(eventId: String): EventEntity {
        return localDataSource.getEventWithoutPhotos(eventId)
    }

    override suspend fun deleteEvent(eventId: String): kotlin.Result<Unit> {
        localDataSource.deleteEvent(eventId)
        val remoteResult = applicationScope.async {
            remoteDataSource.deleteEvent(eventId)
        }.await()

        if (remoteResult.code() == SUCCESS_CODE) {
            Log.e("Event Repository", "Successfully deleted event: $remoteResult")
        } else {
            localDataSource.upsertDeletedEventId(DeletedEventIdEntity(eventId))
            Log.e("Event Repository", "error deleting event: $remoteResult")
        }
        return kotlin.Result.success(Unit)
    }

}
