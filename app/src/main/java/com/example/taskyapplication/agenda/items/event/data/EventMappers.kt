package com.example.taskyapplication.agenda.items.event.data

import android.R.attr.host
import com.example.taskyapplication.agenda.domain.asLocalDateValue
import com.example.taskyapplication.agenda.domain.asLocalTimeValue
import com.example.taskyapplication.agenda.domain.combineDateAndTime
import com.example.taskyapplication.agenda.domain.combineLocalDateAndTime
import com.example.taskyapplication.agenda.domain.convertToLong
import com.example.taskyapplication.agenda.domain.toLocalDateAndTime
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventPhotoEntity
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState
import java.time.ZoneId
import java.time.ZoneOffset

fun EventUiState.toCreateEventNetworkModel(): CreateEventNetworkModel {
    val startDateTime = combineLocalDateAndTime(startDate, startTime).convertToLong()
    val endDateTime = combineLocalDateAndTime(endDate, endTime).convertToLong()

    return CreateEventNetworkModel(
        id = id,
        title = title,
        description = description,
        from = startDateTime,
        to = endDateTime,
        remindAt = 0L,
        attendeeIds = attendeeIds
    )
}

fun EventUiState.toUpdateEventNetworkModel(): UpdateEventNetworkModel {
    val startDateTime = combineLocalDateAndTime(startDate, startTime).convertToLong()
    val endDateTime = combineLocalDateAndTime(endDate, endTime).convertToLong()

    return UpdateEventNetworkModel(
        id = id,
        title = title,
        description = description,
        from = startDateTime,
        to = endDateTime,
        remindAt = 0L,
        attendeeIds = attendeeIds,
        deletedPhotoKeys = deletedPhotoKeys,
        isGoing = isGoingToEvent
    )
}
fun CreateEventNetworkModel.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        title = title,
        description = description,
        startDate = from.toLocalDateAndTime().first,
        startTime = from.toLocalDateAndTime().second,
        endDate = to.toLocalDateAndTime().first,
        endTime = to.toLocalDateAndTime().second,
        remindAt = remindAt,
        photos = emptyList(),
        photoKeys = emptyList(),
        attendeeIds = emptyList(),
        host = "",
        isUserEventCreator = true
    )
}
fun CreatedEventResponse.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        title = title,
        description = description,
        startDate = from.toLocalDateAndTime().first,
        startTime = from.toLocalDateAndTime().second,
        endDate = to.toLocalDateAndTime().first,
        endTime = to.toLocalDateAndTime().second,
        remindAt = remindAt,
        photos = photos.map { it.url },
        photoKeys = photos.map { it.key},
        attendeeIds = attendees.map { it.userId },
        host = host,
        isUserEventCreator = isUserEventCreator
    )
}

fun CreatedEventResponse.toPhotoEntities() : List<EventPhotoEntity> {
    return this.photos.map {
        EventPhotoEntity(
            eventId = id,
            photoKey = it.key,
            photoUrl = it.url
        )
    }
}

fun UpdateEventNetworkModel.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        title = title,
        description = description,
        startDate = from.toLocalDateAndTime().first,
        startTime = from.toLocalDateAndTime().second,
        endDate = to.toLocalDateAndTime().first,
        endTime = to.toLocalDateAndTime().second,
        remindAt = remindAt,
        photos = emptyList(),
        photoKeys = emptyList(),
        attendeeIds = emptyList(),
        host = "",
        isUserEventCreator = true
    )
}
fun UpdatedEventResponse.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        title = title,
        description = description,
        startDate = from.toLocalDateAndTime().first,
        startTime = from.toLocalDateAndTime().second,
        endDate = to.toLocalDateAndTime().first,
        endTime = to.toLocalDateAndTime().second,
        remindAt = remindAt,
        photos = photos.map { it.url },
        photoKeys = photos.map { it.key},
        attendeeIds = attendees.map { it.userId },
        host = host,
        isUserEventCreator = isUserEventCreator
    )
}

fun UpdatedEventResponse.toPhotoEntities() : List<EventPhotoEntity> {
    return this.photos.map {
        EventPhotoEntity(
            eventId = id,
            photoKey = it.key,
            photoUrl = it.url
        )
    }
}
