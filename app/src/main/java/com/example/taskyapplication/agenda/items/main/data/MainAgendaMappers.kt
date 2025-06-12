package com.example.taskyapplication.agenda.items.main.data

import com.example.taskyapplication.agenda.domain.toDateTime
import com.example.taskyapplication.agenda.domain.toFormattedDate
import com.example.taskyapplication.agenda.domain.toFormattedTime
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity

fun EventEntity.toAgendaEventSummary() = AgendaEventSummary(
    id = id,
    title = title,
    description = description,
    startDate = startDate.toDateTime().toFormattedDate(),
    startTime = startTime.toDateTime().toFormattedTime(),
    type = AgendaItemType.EVENT,
    isAttendee = false
)
//fun Event.toEventEntitySummary() = EventSummaryEntity(
//    id = id,
//    title = title,
//    description = description,
//    startDate = from.toDateTime().toFormattedDate(),
//    startTime = from.toDateTime().toFormattedTime(),
//    type = AgendaItemType.EVENT,
//    isAttendee = false
//)

fun TaskEntity.toAgendaTaskSummary() = AgendaTaskSummary(
    id = id,
    title = title,
    description = description,
    startDate = date,
    startTime = time,
    type = AgendaItemType.TASK,
    isDone = isDone
)
//fun Task.toTaskEntitySummary() = TaskSummaryEntity(
//    id = id,
//    title = title,
//    description = description,
//    startDate = time.toDayMonthAsString(),
//    startTime = time.toDateTime().toFormattedTime(),
//    type = AgendaItemType.TASK,
//    isDone = isDone
//)

fun ReminderEntity.toAgendaReminderSummary() = AgendaReminderSummary(
    id = id,
    title = title,
    description = description,
    startDate = date,
    startTime = time,
    type = AgendaItemType.REMINDER
)
//fun Reminder.toReminderEntitySummary() = ReminderSummaryEntity(
//    id = id,
//    title = title,
//    description = description,
//    startDate = time.toDayMonthAsString(),
//    startTime = time.toDateTime().toFormattedTime(),
//    type = AgendaItemType.REMINDER
//)

fun Task.asTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    date = time.toDateTime().toFormattedDate(),
    time = time.toDateTime().toFormattedTime(),
    remindAt = remindAt,
    isDone = isDone
)