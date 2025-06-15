package com.example.taskyapplication.agenda.items.main.data

import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.items.event.data.CreatedEventResponse
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.data.models.ReminderResponse
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class FullAgendaResponse(
    val events: List<CreatedEventResponse>,
    val tasks: List<Task>,
    val reminders: List<ReminderResponse>
)

data class LocalAgendaSummary(
    val events: List<EventEntity>,
    val tasks: List<TaskEntity>,
    val reminders: List<ReminderEntity>
)

interface AgendaSummary {
    val id: String
    val description: String
    val title: String
    val startDate: LocalDate
    val startTime: LocalTime
    val type: AgendaItemType
}

data class AgendaTaskSummary(
    override val id: String = "",
    override val description: String = "",
    override val title: String = "",
    override val startDate: LocalDate = LocalDate.now(),
    override val startTime: LocalTime = LocalTime.now(),
    override val type: AgendaItemType = AgendaItemType.TASK,
    val isDone: Boolean = false
): AgendaSummary

data class AgendaEventSummary(
    override val id: String = "",
    override val description: String = "",
    override val title: String = "",
    override val startDate: LocalDate = LocalDate.now(),
    override val startTime: LocalTime = LocalTime.now(),
    override val type: AgendaItemType = AgendaItemType.EVENT,
    val isAttendee: Boolean = false,
): AgendaSummary


data class AgendaReminderSummary(
    override val id: String = "",
    override val description: String = "",
    override val title: String = "",
    override val startDate: LocalDate = LocalDate.now(),
    override val startTime: LocalTime = LocalTime.now(),
    override val type: AgendaItemType = AgendaItemType.REMINDER,
): AgendaSummary

data class DeletedAgendaItems(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>
)

//data class AgendaScreenCalendar(
//    val dayOfWeek: Char,
//    val dayOfMonth: Int,
//)

enum class AgendaItemType(val color: Int) {
    EVENT(color = R.color.event_card),
    TASK(color = R.color.task_card),
    REMINDER(color = R.color.reminder_card)
}

//enum class AgendaSummaryMenuAction {
//    OPEN,
//    EDIT,
//    DELETE
//}
