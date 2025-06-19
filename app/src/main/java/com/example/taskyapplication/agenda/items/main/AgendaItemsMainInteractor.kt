package com.example.taskyapplication.agenda.items.main

import android.util.Log
import com.example.taskyapplication.agenda.items.event.data.db.EventEntity
import com.example.taskyapplication.agenda.items.event.data.toCreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.toEventEntity
import com.example.taskyapplication.agenda.items.event.data.EventLocalDataSource
import com.example.taskyapplication.agenda.items.event.domain.EventRepository
import com.example.taskyapplication.agenda.items.event.network.EventApiService
import com.example.taskyapplication.agenda.items.main.data.AgendaEventSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.main.data.AgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaTaskSummary
import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.asTaskEntity
import com.example.taskyapplication.agenda.items.main.data.toAgendaEventSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaTaskSummary
import com.example.taskyapplication.agenda.items.main.domain.network.AgendaApiService
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderEntity
import com.example.taskyapplication.agenda.items.reminder.data.models.asReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.toReminderEntity
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderLocalDataSource
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRepository
import com.example.taskyapplication.agenda.items.reminder.domain.network.ReminderApiService
import com.example.taskyapplication.agenda.items.task.data.local.entity.TaskEntity
import com.example.taskyapplication.agenda.items.task.data.mappers.toTaskNetworkModel
import com.example.taskyapplication.agenda.items.task.domain.TaskLocalDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.agenda.items.task.domain.network.TaskApiService
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.di.json
import com.example.taskyapplication.domain.utils.SUCCESS_CODE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate
import javax.inject.Inject

class AgendaItemsMainInteractor @Inject constructor(
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val reminderRepository: ReminderRepository,
    private val authRepository: AuthRepository,
    private val taskLocalDataSource: TaskLocalDataSource,
    private val eventLocalDataSource: EventLocalDataSource,
    private val reminderLocalDataSource: ReminderLocalDataSource,
    private val taskApiService: TaskApiService,
    private val reminderApiService: ReminderApiService,
    private val eventApiService: EventApiService,
    private val agendaApiService: AgendaApiService
) {

    suspend fun syncDeletedItemIds(): Result<Unit> {
        return try {
            val deletedTasks = taskLocalDataSource.getAllDeletedTaskIds()
            val deletedReminders = reminderLocalDataSource.getDeletedReminderIds()
            val deletedEvents = eventLocalDataSource.getDeletedEventIds()

            val deletedAgendaItems = DeletedAgendaItems(
                deletedEventIds = deletedEvents.map { it.id },
                deletedTaskIds = deletedTasks.map { it.id },
                deletedReminderIds = deletedReminders.map { it.id }
            )
            agendaApiService.syncDeletedAgendaIds(deletedAgendaItems)
            Log.d("AgendaItemsMainInteractor", "Successfully synced deleted items")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AgendaItemsMainInteractor", e.message.toString())
            Result.failure(e)
        }
    }

    suspend fun syncLocalItemsWithRemoteStorage(): Result<Unit> {
        val localItems = fetchAllLocalItems()
        val localTasks = localItems.first
        val localReminders = localItems.second
        val localEvents = localItems.third

        localTasks.forEach { task ->
            try {
                val taskToSync = task.toTaskNetworkModel()
                val response = taskApiService.createNewTask(taskToSync)

                if (response.code() == SUCCESS_CODE) {
                    Log.d("CommonDateProvider:", "Successfully synced task")
                } else {
                    Log.e("CommonDateProvider:", "Failed to sync task")
                }
            } catch (e: Exception) {
                Log.e("CommonDataProvider", e.message.toString())
            }
        }

        localReminders.forEach { reminder ->
            try {
                val reminderToSync = reminder.asReminderNetworkModel()
                val response = reminderApiService.createNewReminder(reminderToSync)

                if (response.isSuccessful) {
                    Log.d("CommonDateProvider:", "Successfully synced reminder")
                } else {
                    Log.e("CommonDateProvider:", "Failed to sync reminder")
                }
            } catch (e: Exception) {
                Log.e("CommonDataProvider", e.message.toString())
            }
        }

        localEvents.forEach { event ->
            try {
                val eventToSync = event.toCreateEventNetworkModel()
                val eventRequest = json.encodeToString(eventToSync).toRequestBody()
                val response = eventApiService.createEvent(eventRequest, emptyList())

                if (response.isSuccessful) {
                    Log.d("CommonDateProvider:", "Successfully synced event")
                } else {
                    Log.e("CommonDateProvider:", "Failed to sync event")
                }
            } catch (e: Exception) {
                Log.e("CommonDataProvider", e.message.toString())
            }
        }

        val fullRemoteAgenda = agendaApiService.getFullAgenda()
        val remoteTasks = fullRemoteAgenda.body()?.tasks
        val remoteReminders = fullRemoteAgenda.body()?.reminders
        val remoteEvents = fullRemoteAgenda.body()?.events

        remoteTasks?.forEach { task ->
            val taskToSync = task.asTaskEntity()
            val taskToVerify = taskLocalDataSource.getTask(taskToSync.id)
            if (taskToVerify != null) {
                taskLocalDataSource.upsertTask(taskToSync)
            }
        }

        remoteReminders?.forEach { reminder ->
            val reminderToSync = reminder.toReminderEntity()
            val reminderToVerify = reminderLocalDataSource.getReminder(reminderToSync.id)
            if (reminderToVerify != null) {
                reminderLocalDataSource.upsertReminder(reminderToSync)
            }
        }

        remoteEvents?.forEach { event ->
            val eventToSync = event.toEventEntity()
            val eventToVerify = eventLocalDataSource.getEventById(eventToSync.id)
            if (eventToVerify != null) {
                 eventLocalDataSource.insertEventWithoutPhotos(eventToSync)
            }
        }
        return Result.success(Unit)
    }

    suspend fun logout() {
        authRepository.logoutUser()
        clearLocalDataAfterLogout()
    }

    suspend fun fetchAllLocalItems(): Triple<List<TaskEntity>, List<ReminderEntity>, List<EventEntity>> {
        val allTasks = taskLocalDataSource.getAllTasks()
        val allEvents = eventLocalDataSource.getEventsWithoutPhotos()
        val allReminders = reminderLocalDataSource.getAllReminders()
        return Triple(allTasks, allReminders, allEvents)
    }

    fun buildAgendaForSelectedDate(selectedDate: LocalDate):
            Flow<Triple<List<AgendaTaskSummary>, List<AgendaReminderSummary>,List<AgendaEventSummary>>> {
        val requestedTasks = taskLocalDataSource.getTasksByDate(selectedDate)
            .map { taskEntities ->
                taskEntities.map { it.toAgendaTaskSummary() }
            }

        val requestedReminders = reminderLocalDataSource.getRemindersForDate(selectedDate)
            .map { reminderEntities ->
                reminderEntities.map { it.toAgendaReminderSummary() }
            }

        val requestedEvents = eventLocalDataSource.getEventsForSelectedDate(selectedDate)
            .map { eventEntities ->
                eventEntities.map { it.toAgendaEventSummary() }
            }

        return combine(
            requestedTasks, requestedReminders, requestedEvents
        ) { tasks, reminders, events ->
            Triple(tasks, reminders, events )
        }
    }

    private suspend fun clearLocalDataAfterLogout() {
        taskLocalDataSource.deleteAllTasks()
        reminderLocalDataSource.deleteAllReminders()
        eventLocalDataSource.deleteAllEvents()
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
            AgendaItemType.EVENT -> eventRepository.getEventWithoutImages(itemId)
            AgendaItemType.REMINDER -> reminderRepository.getReminderById(itemId)
        }
    }
}
