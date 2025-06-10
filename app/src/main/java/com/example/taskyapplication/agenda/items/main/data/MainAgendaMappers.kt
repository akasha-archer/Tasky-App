package com.example.taskyapplication.agenda.items.main.data

import com.example.taskyapplication.agenda.domain.toDayMonthAsString
import com.example.taskyapplication.agenda.items.task.data.mappers.toDateTime
import com.example.taskyapplication.agenda.items.task.data.mappers.toFormattedDate
import com.example.taskyapplication.agenda.items.task.data.mappers.toFormattedTime

fun Event.toAgendaEventSummary() = AgendaEventSummary(
    id = id,
    title = title,
    description = description,
    startDate = from.toDateTime().toFormattedDate(),
    startTime = from.toDateTime().toFormattedTime(),
    type = AgendaItemType.EVENT,
    isAttendee = false
)

fun Task.toAgendaTaskSummary() = AgendaTaskSummary(
    id = id,
    title = title,
    description = description,
    startDate = time.toDayMonthAsString(),
    startTime = time.toDateTime().toFormattedTime(),
    type = AgendaItemType.TASK,
    isDone = isDone
)

fun Reminder.toAgendaReminderSummary() = AgendaReminderSummary(
    id = id,
    title = title,
    description = description,
    startDate = time.toDayMonthAsString(),
    startTime = time.toDateTime().toFormattedTime(),
    type = AgendaItemType.REMINDER
)
