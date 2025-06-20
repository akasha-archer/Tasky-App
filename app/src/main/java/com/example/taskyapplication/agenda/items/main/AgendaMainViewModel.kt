package com.example.taskyapplication.agenda.items.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.common.INetworkObserver
import com.example.taskyapplication.agenda.common.NetworkStatus
import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.items.main.data.AgendaEventSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.main.data.AgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaTaskSummary
import com.example.taskyapplication.domain.utils.SystemTimeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaMainViewModel @Inject constructor(
    private val itemsMainInteractor: AgendaItemsMainInteractor,
    private val networkObserver: INetworkObserver,
) : ViewModel() {

    private val _agendaViewState = MutableStateFlow(AgendaMainViewState())
    val agendaViewState: StateFlow<AgendaMainViewState> = _agendaViewState.asStateFlow()

    init {
        // Fetching user name
        viewModelScope.launch {
            val userName = itemsMainInteractor.getUserName() ?: ""
            Log.d("AgendaVM", "Fetched userName in init: '$userName'")
            _agendaViewState.update { it.copy(userFullName = userName) }
        }

        // Observing network status and performing sync operations
        viewModelScope.launch {
            var initialSyncAttempted = false
            networkObserver.networkStatus.collect { status ->
                val isOnline = status == NetworkStatus.Available
                val previouslyOnline = _agendaViewState.value.isUserOnline

                _agendaViewState.update { it.copy(isUserOnline = isOnline) }
                Log.d("AgendaVM", "Network status changed. Is Online: $isOnline")

                if (isOnline) {
                    // Sync if coming online OR if it's the first check and we are online
                    if (!previouslyOnline || !initialSyncAttempted) {
                        Log.d("AgendaVM", "Network is online. Triggering sync.")
                        itemsMainInteractor.syncLocalItemsWithRemoteStorage()
                        itemsMainInteractor.syncDeletedItemIds()
                        initialSyncAttempted = true
                    }
                }
            }
        }
        viewModelScope.launch {
            buildAgendaListForDate(_agendaViewState.value.selectedDate)
        }
    }

    private fun buildAgendaListForDate(selectedDate: LocalDate) {
        Log.e("AgendaVM", "Building agenda for date: $selectedDate")
        viewModelScope.launch {
            itemsMainInteractor.buildAgendaForSelectedDate(selectedDate)
                .collect { (tasks, reminders, events) ->
                    _agendaViewState.update {
                        it.copy(
                            displayDateHeading = showSelectedDate(selectedDate),
                            selectedTasks = tasks,
                            selectedReminders = reminders,
                            selectedEvents = events,
                            combinedSummaryList = _agendaViewState.value.selectedTasks + _agendaViewState.value.selectedReminders + _agendaViewState.value.selectedEvents
                        )
                    }
                }
        }
    }

    private fun showSelectedDate(selectedDate: LocalDate): String {
        Log.e("AgendaVM", "Selected date function: $selectedDate")
        return when (selectedDate) {
            SystemTimeProvider.now.toOffsetDateTime().toZonedDateTime().toLocalDate() -> "Today"
            SystemTimeProvider.now.toLocalDate().plusDays(1) -> "Tomorrow"
            SystemTimeProvider.now.toLocalDate().minusDays(1) -> "Yesterday"
            else -> selectedDate.toDateAsString()
        }
    }

    private fun logoutUser() {
        viewModelScope.launch {
            itemsMainInteractor.logout()
        }
    }

    private fun deleteAgendaItem(itemId: String, type: AgendaItemType) {
        viewModelScope.launch {
            itemsMainInteractor.deleteItemByType(type, itemId)
        }
    }

    private fun getAgendaItem(itemId: String, type: AgendaItemType) {
        viewModelScope.launch {
            itemsMainInteractor.getItemByType(type, itemId)
        }

    }

    fun executeAgendaActions(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.SelectAgendaDate -> {
                val targetAgendaDate = action.selectedDate
                _agendaViewState.value = _agendaViewState.value.copy(
                    selectedDate = targetAgendaDate
                )
                buildAgendaListForDate(targetAgendaDate)
            }

            is MainScreenAction.ItemToDelete -> {
                deleteAgendaItem(action.itemId, action.type)
            }

            is MainScreenAction.ItemToEdit -> {
                getAgendaItem(action.itemId, action.type)
            }

            is MainScreenAction.ItemToOpen -> {
                getAgendaItem(action.itemId, action.type)
            }

            MainScreenAction.LogoutUser -> {
                logoutUser()
            }
        }
    }
}

data class AgendaMainViewState(
    val displayDateHeading: String = "Today",
    val userFullName: String? = "",
    val isUserOnline: Boolean = true,
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedEvents: List<AgendaEventSummary> = emptyList(),
    val selectedTasks: List<AgendaTaskSummary> = emptyList(),
    val selectedReminders: List<AgendaReminderSummary> = emptyList(),
    val combinedSummaryList: List<AgendaSummary> = emptyList()
)
