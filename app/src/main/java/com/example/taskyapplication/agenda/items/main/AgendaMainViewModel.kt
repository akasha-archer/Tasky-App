package com.example.taskyapplication.agenda.items.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.items.main.data.AgendaEventSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.main.data.AgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaTaskSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaEventSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaTaskSummary
import com.example.taskyapplication.agenda.items.main.domain.AgendaOfflineFirstRepository
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
    private val repository: AgendaOfflineFirstRepository,
    private val commonDataProvider: AgendaCommonDataProvider
) : ViewModel() {

    private val _agendaViewState = MutableStateFlow(AgendaMainViewState())
    val agendaViewState = _agendaViewState
        .onStart { buildAgendaListForDate(_agendaViewState.value.selectedDate) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AgendaMainViewState(),
        )

    private fun buildAgendaListForDate(selectedDate: Long) {
        viewModelScope.launch {
            val result = repository.getAgendaItemsForDate(selectedDate)
            _agendaViewState.update {
                it.copy(
                    displayDateHeading = selectedDate.toDateAsString().ifEmpty { "Today" },
                    selectedEvents = result.body()?.events.orEmpty()
                        .map { it.toAgendaEventSummary() },
                    selectedTasks = result.body()?.tasks.orEmpty().map { it.toAgendaTaskSummary() },
                    selectedReminders = result.body()?.reminders.orEmpty()
                        .map { it.toAgendaReminderSummary() },
                    combinedSummaryList = _agendaViewState.value.selectedEvents + _agendaViewState.value.selectedTasks + _agendaViewState.value.selectedReminders
                )
            }
        }
    }

    private fun logoutUser() {}

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
                buildAgendaListForDate(action.selectedDate)
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
    val selectedDate: Long = LocalDate.now().toEpochDay(),
    val selectedEvents: List<AgendaEventSummary> = emptyList(),
    val selectedTasks: List<AgendaTaskSummary> = emptyList(),
    val selectedReminders: List<AgendaReminderSummary> = emptyList(),
    val combinedSummaryList: List<AgendaSummary> = emptyList()
)
