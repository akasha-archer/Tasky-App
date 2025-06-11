package com.example.taskyapplication.agenda.items.main

import com.example.taskyapplication.agenda.items.event.data.db.EventDao
import com.example.taskyapplication.agenda.items.event.domain.EventRepository
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderDao
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRepository
import com.example.taskyapplication.agenda.items.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.auth.domain.AuthRepository
import javax.inject.Inject

class AgendaCommonDataProvider @Inject constructor(
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val reminderRepository: ReminderRepository,
    private val authRepository: AuthRepository,
    private val eventDao: EventDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao
) {
    suspend fun logout() {
        authRepository.logoutUser()
        clearLocalDataAfterLogout()
    }

    private suspend fun clearLocalDataAfterLogout() {
        eventDao.deleteAllEvents()
        taskDao.deleteAllTasks()
        reminderDao.deleteAllReminders()
    }

    suspend fun deleteItemByType(type: AgendaItemType, itemId: String) {
        when (type) {
            AgendaItemType.TASK -> taskRepository.deleteTask(itemId)
            AgendaItemType.EVENT -> eventRepository.deleteEvent(itemId)
            AgendaItemType.REMINDER -> reminderRepository.deleteReminder(itemId)
        }
    }

    suspend fun getItemByType(type: AgendaItemType, itemId: String): Any {
        return when (type) {
            AgendaItemType.TASK -> taskRepository.getTaskById(itemId)
            AgendaItemType.EVENT -> eventRepository.getEventById(itemId)
            AgendaItemType.REMINDER -> reminderRepository.getReminderById(itemId)
        }
    }
}
