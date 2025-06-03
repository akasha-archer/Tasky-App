package com.example.taskyapplication.agenda.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.reminder.domain.ReminderRepository
import com.example.taskyapplication.agenda.reminder.presentation.ReminderUiState
import com.example.taskyapplication.agenda.task.presentation.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
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
            AgendaItemAction.SaveAgendaItemUpdates -> {
                val newTitle = _reminderUiState.value.title
                val newDescription = _reminderUiState.value.description
                val reminderStartTime = _reminderUiState.value.time
                val reminderDate = _reminderUiState.value.date
                val reminderNotificationTime = _reminderUiState.value.remindAt

                if (newTitle.isBlank() && newDescription.isBlank()) {
                    return
                }
                val newTaskId: String = UUID.randomUUID().toString()
                _reminderUiState.update {
                    it.copy(
                        id = newTaskId
                    )
                }
                val reminderUiState = ReminderUiState(
                    id = newTaskId,
                    title = newTitle,
                    description = newDescription,
                    time = reminderStartTime,
                    date = reminderDate,
                    remindAt = reminderNotificationTime
                )
                viewModelScope.launch {
//                    reminderRepository.createNewReminder(
//
//                    )
                }
                _reminderUiState.update {
                    it.copy(isEditingItem = false)
                }
            }

            is AgendaItemAction.SetTitle -> {
                viewModelScope.launch {
                    _reminderUiState.update { it.copy(title = action.title) }
                }
            }

            is AgendaItemAction.SetDate -> {
                _reminderUiState.update { it.copy(
                    date = action.date,
                    isEditingDate = false
                ) }
            }

            is AgendaItemAction.SetDescription -> {
                _reminderUiState.update { it.copy(description = action.description) }
            }

            is AgendaItemAction.SetReminderTime -> {
                _reminderUiState.update { it.copy(remindAt = action.reminder) }
            }

            is AgendaItemAction.SetTime -> {
                _reminderUiState.update {
                    it.copy(
                        time = action.time,
                        isEditingTime = false
                    )
                }
            }

            AgendaItemAction.ShowDatePicker -> {
                _reminderUiState.update { it.copy(isEditingDate = true) }
            }

            AgendaItemAction.HideDatePicker -> {
                _reminderUiState.update {
                    it.copy(isEditingDate = false)
                }
            }

            AgendaItemAction.ShowReminderDropDown -> {
                _reminderUiState.update {
                    it.copy(isEditingReminder = true)
                }
            }

            AgendaItemAction.HideReminderDropDown -> {
                _reminderUiState.update {
                    it.copy(isEditingReminder = false)
                }
            }

            AgendaItemAction.ShowTimePicker -> {
                _reminderUiState.update {
                    it.copy(isEditingTime = true)
                }
            }

            AgendaItemAction.HideTimePicker -> {
                _reminderUiState.update {
                    it.copy(isEditingTime = false)
                }
            }

            AgendaItemAction.CloseEditDescriptionScreen -> {
                _reminderUiState.update {
                    it.copy(isEditingItem = false)
                }
            }

            AgendaItemAction.CloseEditTitleScreen -> {
                _reminderUiState.update {
                    it.copy(isEditingItem = false)
                }
            }

            AgendaItemAction.LaunchEditDescriptionScreen -> {
                _reminderUiState.update {
                    it.copy(isEditingItem = true)
                }
            }

            AgendaItemAction.LaunchEditTitleScreen -> {
                _reminderUiState.update {
                    it.copy(isEditingItem = true)
                }
            }

            AgendaItemAction.CancelEdit -> {
                _reminderUiState.update {
                    it.copy(
                        isEditingItem = false,
                        isEditingDate = false,
                        isEditingTime = false,
                        isEditingReminder = false
                    )
                }
            }

            AgendaItemAction.LaunchDateTimeEditScreen -> {
                _reminderUiState.update {
                    it.copy(isEditingItem = true)
                }
            }

            AgendaItemAction.CloseDetailScreen -> {
                Unit
                // go back to Agenda screen
            }

            AgendaItemAction.SaveDateTimeEdit -> {
                _reminderUiState.update {
                    it.copy(isEditingItem = false)
                }
            }
        }
    }
}
