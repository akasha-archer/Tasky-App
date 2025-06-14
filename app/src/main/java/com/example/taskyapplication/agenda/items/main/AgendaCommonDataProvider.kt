package com.example.taskyapplication.agenda.items.main

import com.example.taskyapplication.agenda.items.event.domain.EventLocalDataSource
import com.example.taskyapplication.agenda.items.event.domain.EventRepository
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.main.data.AgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaTaskSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaTaskSummary
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderLocalDataSource
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRepository
import com.example.taskyapplication.agenda.items.task.domain.TaskLocalDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.auth.domain.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class AgendaCommonDataProvider @Inject constructor(
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val reminderRepository: ReminderRepository,
    private val authRepository: AuthRepository,
    private val taskLocalDataSource: TaskLocalDataSource,
    private val eventLocalDataSource: EventLocalDataSource,
    private val reminderLocalDataSource: ReminderLocalDataSource,

) {
    suspend fun logout() {
        authRepository.logoutUser()
        clearLocalDataAfterLogout()
    }

    fun buildAgendaForSelectedDate(selectedDate: LocalDate):
            Flow<Pair<List<AgendaTaskSummary>, List<AgendaReminderSummary>>> {
        val requestedTasks = taskLocalDataSource.getTasksByDate(selectedDate)
            .map { taskEntities ->
                taskEntities.map { it.toAgendaTaskSummary() }
            }

        val requestedReminders = reminderLocalDataSource.getRemindersForDate(selectedDate)
            .map { reminderEntities ->
                reminderEntities.map { it.toAgendaReminderSummary() }
            }
        return combine(
            requestedTasks, requestedReminders
        ) { tasks, reminders ->
            Pair(tasks, reminders)
        }
    }

    private suspend fun clearLocalDataAfterLogout() {
        taskLocalDataSource.deleteAllTasks()
        reminderLocalDataSource.deleteAllReminders()
    }

    suspend fun deleteItemByType(type: AgendaItemType, itemId: String) {
        when (type) {
            AgendaItemType.TASK -> taskRepository.deleteTask(itemId)
            AgendaItemType.EVENT -> eventRepository.deleteEvent(itemId)
            AgendaItemType.REMINDER -> reminderRepository.deleteReminder(itemId)
        }
    }

    suspend fun getItemByType(type: AgendaItemType, itemId: String): Any? {
        return when (type) {
            AgendaItemType.TASK -> taskRepository.getTaskById(itemId)
            AgendaItemType.EVENT -> eventRepository.getEventById(itemId)
            AgendaItemType.REMINDER -> reminderRepository.getReminderById(itemId)
        }
    }
}
