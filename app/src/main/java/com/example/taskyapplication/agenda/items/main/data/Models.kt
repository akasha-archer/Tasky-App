package com.example.taskyapplication.agenda.items.main.data

import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors

// event light green
// tasks dark green
// reminders gray

data class AgendaScreenUi(
    val displayDateHeading: String,
    val selectedDate: String,
    val itemsForSelectedDate: List<AgendaItemSummary>
)

data class AgendaItemSummary(
    val description: String,
    val title: String,
    val date: String,
    val time: String,
    val type: String
)

data class deletedAgendaItems(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>
)

enum class AgendaItemType(val color: Int) {
    EVENT(color = R.color.event_card),
    TASK(color = R.color.task_card),
    REMINDER(color = R.color.reminder_card)
}