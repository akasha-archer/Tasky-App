package com.example.taskyapplication.agenda.items.main

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
import com.example.taskyapplication.auth.domain.AuthTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaMainViewModel @Inject constructor(
    private val commonDataProvider: AgendaItemsMainInteractor,
    private val networkObserver: INetworkObserver,
    private val authTokenManager: AuthTokenManager
) : ViewModel() {

    private val _agendaViewState = MutableStateFlow(AgendaMainViewState())
    val agendaViewState = _agendaViewState
        .onStart {
            networkObserver.networkStatus.collect { status ->
                val isOnline = status == NetworkStatus.Available
                _agendaViewState.update { it.copy(isUserOnline = isOnline) }
            }
            updateUserName()
            buildAgendaListForDate(_agendaViewState.value.selectedDate)
            if (_agendaViewState.value.isUserOnline) {
                commonDataProvider.syncLocalItemsWithRemoteStorage()
                commonDataProvider.syncDeletedItemIds()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AgendaMainViewState(),
        )

    private fun updateUserName() {
        viewModelScope.launch {
            _agendaViewState.update { it.copy(userFullName = authTokenManager.readUserFullName() ?: "ZZ") }
        }
    }

    private fun buildAgendaListForDate(selectedDate: LocalDate) {
        viewModelScope.launch {
            commonDataProvider.buildAgendaForSelectedDate(selectedDate)
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
        return when (selectedDate) {
            LocalDate.now() -> "Today"
            LocalDate.now().plusDays(1) -> "Tomorrow"
            LocalDate.now().minusDays(1) -> "Yesterday"
            else -> selectedDate.toDateAsString()
        }
    }

    private fun logoutUser() {
        viewModelScope.launch {
            commonDataProvider.logout()
        }
    }

    private fun deleteAgendaItem(itemId: String, type: AgendaItemType) {
        viewModelScope.launch {
            commonDataProvider.deleteItemByType(type, itemId)
        }
    }

    private fun getAgendaItem(itemId: String, type: AgendaItemType) {
        viewModelScope.launch {
            commonDataProvider.getItemByType(type, itemId)
        }

    }

    fun executeAgendaActions(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.SelectAgendaDate -> {
                _agendaViewState.value = _agendaViewState.value.copy(
                    selectedDate = action.selectedDate
                )
                buildAgendaListForDate(_agendaViewState.value.selectedDate)
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
    val userFullName: String = "",
    val isUserOnline: Boolean = true,
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedEvents: List<AgendaEventSummary> = emptyList(),
    val selectedTasks: List<AgendaTaskSummary> = emptyList(),
    val selectedReminders: List<AgendaReminderSummary> = emptyList(),
    val combinedSummaryList: List<AgendaSummary> = emptyList()
)
