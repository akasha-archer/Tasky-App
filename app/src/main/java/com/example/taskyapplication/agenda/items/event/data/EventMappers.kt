package com.example.taskyapplication.agenda.items.event.data

import com.example.taskyapplication.agenda.domain.combineDateAndTime
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState

fun EventUiState.toCreateEventNetworkModel(): CreateEventNetworkModel {
    val startDateTime = combineDateAndTime(startDate, startTime)
    val endDateTime = combineDateAndTime(endDate, endTime)
    val reminderTimeMillis = startDateTime.minus(remindAt.asLong)

    return CreateEventNetworkModel(
        id = id,
        title = title,
        description = description,
        from = startDateTime,
        to = endDateTime,
        remindAt = reminderTimeMillis,
        attendeeIds = attendeeIds
    )
}

fun EventUiState.toUpdateEventNetworkModel(): UpdateEventNetworkModel {
    val startDateTime = combineDateAndTime(startDate, startTime)
    val endDateTime = combineDateAndTime(endDate, endTime)
    val reminderTimeMillis = startDateTime.minus(remindAt.asLong)

    return UpdateEventNetworkModel(
        id = id,
        title = title,
        description = description,
        from = startDateTime,
        to = endDateTime,
        remindAt = reminderTimeMillis,
        attendeeIds = attendeeIds,
        deletedPhotoKeys = deletedPhotoKeys,
        isGoing = isGoingToEvent
    )
}


