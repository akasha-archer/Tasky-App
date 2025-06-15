package com.example.taskyapplication.agenda.items.event.data

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import com.example.taskyapplication.agenda.domain.combineLocalDateAndTime
import com.example.taskyapplication.agenda.domain.convertToLong
import com.example.taskyapplication.agenda.domain.toLocalDateAndTime
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.event.data.db.EventPhotoEntity
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState
import java.time.LocalDateTime

fun EventEntity.toEventUiState(): EventUiState {
    return EventUiState(
        id = id,
        title = title,
        description = description,
        photos = photos,
        attendeeIds = attendeeIds,
        startTime = startTime,
        startDate = startDate,
        endTime = endTime,
        endDate = endDate,
        remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE,
    )
}

fun EventEntity.toCreateEventNetworkModel(): CreateEventNetworkModel {
    val eventStart = LocalDateTime.of(startDate, startTime).convertToLong()
    val eventEnd = LocalDateTime.of(endDate, endTime).convertToLong()

    return CreateEventNetworkModel(
        id = id,
        title = title,
        description = description,
        from = eventStart,
        to = eventEnd,
        remindAt = ReminderOptions.THIRTY_MINUTES_BEFORE.asLong,
        attendeeIds = emptyList(),
    )
}
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
