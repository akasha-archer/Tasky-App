package com.example.taskyapplication.agenda.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.reminder.domain.ReminderRepository
import com.example.taskyapplication.agenda.reminder.presentation.ReminderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SharedReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _reminderUiState = MutableStateFlow(ReminderUiState())
    val reminderUiState = _reminderUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ReminderUiState()
    )

    fun executeActions(action: AgendaItemAction) {
        when (action) {
            AgendaItemAction.SaveAgendaItemUpdates -> TODO()
            AgendaItemAction.CancelEdit -> TODO()
            AgendaItemAction.CloseDetailScreen -> TODO()
            AgendaItemAction.CloseEditDescriptionScreen -> TODO()
            AgendaItemAction.CloseEditTitleScreen -> TODO()
            AgendaItemAction.HideDatePicker -> TODO()
            AgendaItemAction.HideReminderDropDown -> TODO()
            AgendaItemAction.HideTimePicker -> TODO()
            AgendaItemAction.LaunchDateTimeEditScreen -> TODO()
            AgendaItemAction.LaunchEditDescriptionScreen -> TODO()
            AgendaItemAction.LaunchEditTitleScreen -> TODO()
            AgendaItemAction.SaveDateTimeEdit -> TODO()
            is AgendaItemAction.SetDate -> TODO()
            is AgendaItemAction.SetDescription -> TODO()
            is AgendaItemAction.SetReminderTime -> TODO()
            is AgendaItemAction.SetTime -> TODO()
            is AgendaItemAction.SetTitle -> TODO()
            AgendaItemAction.ShowDatePicker -> TODO()
            AgendaItemAction.ShowReminderDropDown -> TODO()
            AgendaItemAction.ShowTimePicker -> TODO()
        }
    }

}
