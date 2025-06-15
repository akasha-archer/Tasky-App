package com.example.taskyapplication.agenda.items.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.common.NetworkStatusObserver
import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.items.main.data.AgendaEventSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.main.data.AgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaTaskSummary
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
    private val networkStatusObserver: NetworkStatusObserver,
) : ViewModel() {

     fun isDeviceConnectedToInternet() = networkStatusObserver.isOnline()

    init {
        viewModelScope.launch {
            if (isDeviceConnectedToInternet()) {
                commonDataProvider.syncLocalItemsWithRemoteStorage()
                commonDataProvider.syncDeletedItemIds()
            }
        }
    }

    private val _agendaViewState = MutableStateFlow(AgendaMainViewState())
    val agendaViewState = _agendaViewState
        .onStart { buildAgendaListForDate(_agendaViewState.value.selectedDate) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AgendaMainViewState(),
        )

    private fun buildAgendaListForDate(selectedDate: LocalDate) {
        viewModelScope.launch {
            commonDataProvider.buildAgendaForSelectedDate(selectedDate)
                .collect { (tasks, reminders) ->
                    _agendaViewState.update {
                        it.copy(
                            displayDateHeading = showSelectedDate(selectedDate),
                            selectedTasks = tasks,
                            selectedReminders = reminders,
                            combinedSummaryList = _agendaViewState.value.selectedTasks + _agendaViewState.value.selectedReminders
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
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedEvents: List<AgendaEventSummary> = emptyList(),
    val selectedTasks: List<AgendaTaskSummary> = emptyList(),
    val selectedReminders: List<AgendaReminderSummary> = emptyList(),
    val combinedSummaryList: List<AgendaSummary> = emptyList()
)
