package com.example.taskyapplication.agenda.items.main.data

import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.items.main.data.db.EventSummaryEntity
import com.example.taskyapplication.agenda.items.main.data.db.ReminderSummaryEntity
import com.example.taskyapplication.agenda.items.main.data.db.TaskSummaryEntity
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class FullAgenda(
    val events: List<Event>,
    val tasks: List<Task>,
    val reminders: List<Reminder>
)

data class LocalAgendaSummary(
    val events: List<EventSummaryEntity>,
    val tasks: List<TaskSummaryEntity>,
    val reminders: List<ReminderSummaryEntity>
)

interface AgendaSummary {
    val id: String
    val description: String
    val title: String
    val startDate: String
    val startTime: String
    val type: AgendaItemType
}

data class AgendaTaskSummary(
    override val id: String = "",
    override val description: String = "",
    override val title: String = "",
    override val startDate: String = "",
    override val startTime: String = "",
    override val type: AgendaItemType = AgendaItemType.TASK,
    val isDone: Boolean = false
): AgendaSummary

data class AgendaEventSummary(
    override val id: String = "",
    override val description: String = "",
    override val title: String = "",
    override val startDate: String = "",
    override val startTime: String = "",
    override val type: AgendaItemType = AgendaItemType.EVENT,
    val isAttendee: Boolean = false,
): AgendaSummary


data class AgendaReminderSummary(
    override val id: String = "",
    override val description: String = "",
    override val title: String = "",
    override val startDate: String = "",
    override val startTime: String = "",
    override val type: AgendaItemType = AgendaItemType.REMINDER,
): AgendaSummary

data class AgendaScreenUi(
    val displayDateHeading: String = "Today",
    val selectedDate: String = LocalDate.now().toString(),
    val itemsForSelectedDate: List<AgendaSummary> = emptyList()
)

data class DeletedAgendaItems(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>
)

data class AgendaScreenCalendar(
    val dayOfWeek: Char,
    val dayOfMonth: Int,
)

enum class AgendaItemType(val color: Int) {
    EVENT(color = R.color.event_card),
    TASK(color = R.color.task_card),
    REMINDER(color = R.color.reminder_card)
}

enum class AgendaSummaryMenuAction {
    OPEN,
    EDIT,
    DELETE
}
