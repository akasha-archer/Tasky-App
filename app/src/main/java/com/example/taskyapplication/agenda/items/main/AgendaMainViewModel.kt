package com.example.taskyapplication.agenda.items.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.domain.toDayMonthAsString
import com.example.taskyapplication.agenda.items.main.data.AgendaEventSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaItemsResponse
import com.example.taskyapplication.agenda.items.main.data.AgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaSummary
import com.example.taskyapplication.agenda.items.main.data.AgendaTaskSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaEventSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaReminderSummary
import com.example.taskyapplication.agenda.items.main.data.toAgendaTaskSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaMainViewModel @Inject constructor(

): ViewModel() {

    private val _agendaViewState = MutableStateFlow(AgendaMainViewState())
    val agendaViewState = _agendaViewState
        .onStart { buildAgendaListForDate(_agendaViewState.value.selectedDate) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AgendaMainViewState(),
        )

    private val _fetchAgendaResponse = MutableStateFlow(
        AgendaItemsResponse(
        events = emptyList(),
        tasks = emptyList(),
        reminders = emptyList()
    ))
    val fetchAgendaResponse = _fetchAgendaResponse.asStateFlow()

    private fun buildAgendaListForDate(selectedDate: Long) {
        viewModelScope.launch {
            val fullAgenda = _fetchAgendaResponse.value
            _agendaViewState.update {
                it.copy(
                    displayDateHeading = selectedDate.toDateAsString().ifEmpty { "Today" },
                    todayEvents = fullAgenda.events.map { event -> event.toAgendaEventSummary() }.filter { it.startDate == selectedDate.toDayMonthAsString() },
                    todayTasks = fullAgenda.tasks.map { task -> task.toAgendaTaskSummary() }.filter { it.startDate == selectedDate.toDayMonthAsString() },
                    todayReminders = fullAgenda.reminders.map { reminder -> reminder.toAgendaReminderSummary() }.filter { it.startDate == selectedDate.toDayMonthAsString() },
                    combinedSummaryList = _agendaViewState.value.todayEvents + _agendaViewState.value.todayTasks + _agendaViewState.value.todayReminders
                )
            }
        }
    }

    private fun fetchFullAgenda() {
        viewModelScope.launch {
            // repository fetchAgenda
            // fetchAgendaResponse.update
        }
    }

    private fun logoutUser() {}

    fun executeAgendaActions(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.SelectAgendaDate -> {
                _agendaViewState.value = _agendaViewState.value.copy(
                    selectedDate = action.selectedDate
                )
                buildAgendaListForDate(action.selectedDate)
            }
            is MainScreenAction.LaunchItemMenu -> {
                // try show/hide state in composable
            }
            MainScreenAction.LogoutUser -> { logoutUser() }

        }
    }
}

data class AgendaMainViewState(
    val displayDateHeading: String = "Today",
    val selectedDate: Long = LocalDate.now().toEpochDay(),
    val todayEvents: List<AgendaEventSummary> = emptyList(),
    val todayTasks: List<AgendaTaskSummary> = emptyList(),
    val todayReminders: List<AgendaReminderSummary> = emptyList(),
    val combinedSummaryList: List<AgendaSummary> = emptyList()
)
