package com.example.taskyapplication.agenda.items.main.data

import com.example.taskyapplication.R
import java.time.LocalDate

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

data class deletedAgendaItems(
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

enum class AgendaSummaryMenuOption {
    OPEN,
    EDIT,
    DELETE
}

//sealed class AgendaSummary(
//    private val type: AgendaItemType,
//    private val id: String,
//    private val title: String,
//    private val description: String,
//    private val startDate: String,
//    private val startTime: String) {
//     class EventSummary(
//         type: AgendaItemType = AgendaItemType.EVENT,
//         id: String,
//         description: String,
//         title: String,
//         startDate: String,
//         startTime: String,
//        val isAttendee: Boolean = false,
//    ): AgendaSummary(type, id, title, description, startDate, startTime)
//    class TaskSummary(
//        type: AgendaItemType = AgendaItemType.EVENT,
//        id: String,
//        description: String,
//        title: String,
//        startDate: String,
//        startTime: String,
//        val isDone: Boolean = false,
//    ): AgendaSummary(type, id, title, description, startDate, startTime)
//    class ReminderSummary(
//        type: AgendaItemType = AgendaItemType.EVENT,
//        id: String,
//        description: String,
//        title: String,
//        startDate: String,
//        startTime: String,
//    ): AgendaSummary(type, id, title, description, startDate, startTime)
//}