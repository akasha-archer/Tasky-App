package com.example.taskyapplication.agenda.items.reminder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.common.AgendaItemEvent
import com.example.taskyapplication.agenda.items.reminder.data.models.toReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.data.models.toReminderUiState
import com.example.taskyapplication.agenda.items.reminder.data.models.toUpdateReminderNetworkModel
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRepository
import com.example.taskyapplication.agenda.items.reminder.presentation.ReminderUiState
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SharedReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val reminderId: String? = savedStateHandle.get<String>("reminderId")

    init {
        if (reminderId != null) {
            loadExistingReminder(reminderId)
        }
    }

    private val agendaEventChannel = Channel<AgendaItemEvent>()
    val agendaEvents = agendaEventChannel.receiveAsFlow()

    private val _reminderUiState = MutableStateFlow(ReminderUiState())
    val reminderUiState = _reminderUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ReminderUiState()
    )

    private fun loadExistingReminder(reminderId: String) {
        viewModelScope.launch {
            val requestedReminder = reminderRepository.getReminderById(reminderId)
            if (requestedReminder != null) {
                _reminderUiState.value = requestedReminder.toReminderUiState()
            }
        }
    }

    private fun isNewReminder(currentId: String) = currentId.isEmpty() || currentId.isBlank()

    private fun createOrUpdateReminder() {
        viewModelScope.launch {
            // Implementation for creating or updating a task
            _reminderUiState.update { it.copy(isEditingItem = true) }
            val newTitle = _reminderUiState.value.title
            val newDescription = _reminderUiState.value.description
            val reminderTime = _reminderUiState.value.time
            val reminderDate = _reminderUiState.value.date
            val reminderNotificationTime = _reminderUiState.value.remindAt
            val currentTaskId = _reminderUiState.value.id

            var newTaskId = ""
            if (isNewReminder(currentTaskId)) {
                _reminderUiState.update {
                    it.copy(
                        id = UUID.randomUUID().toString()
                    )
                }
                newTaskId = _reminderUiState.value.id
            }

            val reminderToCreateOrUpdate = ReminderUiState(
                id = if (isNewReminder(currentTaskId)) newTaskId else currentTaskId,
                title = newTitle,
                description = newDescription,
                time = reminderTime,
                date = reminderDate,
                remindAt = reminderNotificationTime
            )

            val result = if (isNewReminder(currentTaskId)) {
                reminderRepository.createNewReminder(
                    reminderToCreateOrUpdate.toReminderNetworkModel()
                )
            } else {
                reminderRepository.updateReminder(
                    reminderToCreateOrUpdate.toUpdateReminderNetworkModel()
                )
            }

            _reminderUiState.update {
                it.copy(isEditingItem = false)
            }
            when {
                result.isSuccess -> {
                    agendaEventChannel.send(AgendaItemEvent.NewItemCreatedSuccess)
                }

                result.isFailure -> {
                    agendaEventChannel.send(
                        AgendaItemEvent.NewItemCreatedError(
                            "Something went wrong. Please try again later."
                        )
                    )
                }
            }
        }
    }


    private fun deleteReminder(reminderId: String) {
        viewModelScope.launch {
            _reminderUiState.update { it.copy(isDeletingItem = true) }
            val result = reminderRepository.deleteReminder(reminderId)
            _reminderUiState.update { it.copy(isDeletingItem = false) }

            when {
                result.isSuccess -> {
                    agendaEventChannel.send(AgendaItemEvent.NewItemCreatedSuccess)
                }

                result.isFailure -> {
                    agendaEventChannel.send(
                        AgendaItemEvent.NewItemCreatedError(
                            "Something went wrong. Please try again later."
                        )
                    )
                }
            }
        }
    }

    fun executeActions(action: AgendaItemAction) {
        when (action) {
            AgendaItemAction.SaveAgendaItemUpdates -> {
                createOrUpdateReminder()
            }

            is AgendaItemAction.EditExistingReminder -> {
                loadExistingReminder(action.id)
            }

            is AgendaItemAction.OpenExistingReminder -> {
                loadExistingReminder(action.id)
            }

            is AgendaItemAction.OpenExistingTask -> {
                Unit
            }

            is AgendaItemAction.EditExistingTask -> {
                Unit
            }

            is AgendaItemAction.SetTitle -> {
                viewModelScope.launch {
                    _reminderUiState.update { it.copy(title = action.title) }
                }
            }

            is AgendaItemAction.SetDate -> {
                _reminderUiState.update {
                    it.copy(
                        date = action.date,
                        isEditingDate = false
                    )
                }
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

            is AgendaItemAction.DeleteItem -> {
                deleteReminder(action.id)
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
