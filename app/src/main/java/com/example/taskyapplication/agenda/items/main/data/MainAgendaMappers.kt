package com.example.taskyapplication.agenda.items.main.data

import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.domain.toTimeAsString
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import java.time.LocalDateTime

fun EventEntity.toAgendaEventSummary() = AgendaEventSummary(
    id = id,
    title = title,
    description = description,
    startDate = startDate,
    startTime = startTime,
    type = AgendaItemType.EVENT,
    isAttendee = false
)

fun TaskEntity.toAgendaTaskSummary() = AgendaTaskSummary(
    id = id,
    title = title,
    description = description,
    startDate = date,
    startTime = time,
    type = AgendaItemType.TASK,
    isDone = isDone
)

fun ReminderEntity.toAgendaReminderSummary() = AgendaReminderSummary(
    id = id,
    title = title,
    description = description,
    startDate = date,
    startTime = time,
    type = AgendaItemType.REMINDER
)

fun Task.asTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    date = LocalDateTime.parse(time.toDateAsString()).toLocalDate(),
    time = LocalDateTime.parse(time.toTimeAsString()).toLocalTime(),
    remindAt = remindAt,
    isDone = isDone
)