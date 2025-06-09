package com.example.taskyapplication.agenda.items.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.core.AgendaItemEvent
import com.example.taskyapplication.agenda.items.task.data.mappers.asTaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.mappers.asUpdateTaskNetworkModel
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.agenda.items.task.presentation.TaskUiState
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
class SharedTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val agendaEventChannel = Channel<AgendaItemEvent>()
    val agendaEvents = agendaEventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = TaskUiState(),
    )

    private fun isNewTask(currentId: String) = currentId.isEmpty() || currentId.isBlank()

    private fun createOrUpdateTask() {
        viewModelScope.launch {
            // Implementation for creating or updating a task
            _uiState.update { it.copy(isEditingItem = true) }
            val newTitle = _uiState.value.title
            val newDescription = _uiState.value.description
            val taskTime = _uiState.value.time
            val taskDate = _uiState.value.date
            val reminderTime = _uiState.value.remindAt
            val currentTaskId = _uiState.value.id

            var newTaskId = ""
            if (isNewTask(currentTaskId)) {
                _uiState.update {
                    it.copy(
                        id = UUID.randomUUID().toString()
                    )
                }
                newTaskId = _uiState.value.id
            }

            val taskToCreateOrUpdate = TaskUiState(
                id = if (isNewTask(currentTaskId)) newTaskId else currentTaskId,
                title = newTitle,
                description = newDescription,
                time = taskTime,
                date = taskDate,
                remindAt = reminderTime
            )
//            if (newTitle.isBlank() || newDescription.isBlank()) {
//                agendaEventChannel.send(
//                    AgendaItemEvent.NewItemCreatedError(
//                        "Title and description cannot be empty."
//                    )
//                )

            val result = if (isNewTask(currentTaskId)) {
                repository.createNewTask(
                    taskToCreateOrUpdate.asTaskNetworkModel()
                )
            } else {
                repository.updateTask(
                    taskToCreateOrUpdate.asUpdateTaskNetworkModel()
                )
            }

            _uiState.update {
                it.copy(isEditingItem = false)
            }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.NO_INTERNET) {
                        agendaEventChannel.send(
                            AgendaItemEvent.NewItemCreatedError(
                                errorMessage = "Item not updated. Please check your internet connection."
                            )
                        )
                    } else {
                        agendaEventChannel.send(
                            AgendaItemEvent.NewItemCreatedError(
                                "Something went wrong. Please try again later."
                            )
                        )
                    }
                }

                is Result.Success -> {
                    agendaEventChannel.send(AgendaItemEvent.NewItemCreatedSuccess)
                }
            }
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDeletingItem = true) }
            val result = repository.deleteTask(taskId)
            _uiState.update { it.copy(isDeletingItem = false) }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.NO_INTERNET) {
                        agendaEventChannel.send(
                            AgendaItemEvent.DeleteError(
                                errorMessage = "Item not deleted. Please check your internet connection."
                            )
                        )
                    } else {
                        agendaEventChannel.send(
                            AgendaItemEvent.DeleteError(
                                "Something went wrong. Please try again later."
                            )
                        )
                    }
                }

                is Result.Success -> {
                    agendaEventChannel.send(AgendaItemEvent.DeleteSuccess)
                }
            }
        }
    }

    fun executeActions(action: AgendaItemAction) {
        when (action) {
            AgendaItemAction.SaveAgendaItemUpdates -> createOrUpdateTask()
            is AgendaItemAction.SetTitle -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(title = action.title) }
                }
            }

            is AgendaItemAction.SetDate -> {
                _uiState.update {
                    it.copy(
                        date = action.date,
                        isEditingDate = false
                    )
                }
            }

            is AgendaItemAction.SetDescription -> {
                _uiState.update { it.copy(description = action.description) }
            }

            is AgendaItemAction.SetReminderTime -> {
                _uiState.update { it.copy(remindAt = action.reminder) }
            }

            is AgendaItemAction.SetTime -> {
                _uiState.update {
                    it.copy(
                        time = action.time,
                        isEditingTime = false
                    )
                }
            }

            is AgendaItemAction.DeleteItem -> deleteTask(action.id)

            AgendaItemAction.ShowDatePicker -> {
                _uiState.update { it.copy(isEditingDate = true) }
            }

            AgendaItemAction.HideDatePicker -> {
                _uiState.update {
                    it.copy(isEditingDate = false)
                }
            }

            AgendaItemAction.ShowReminderDropDown -> {
                _uiState.update {
                    it.copy(isEditingReminder = true)
                }
            }

            AgendaItemAction.HideReminderDropDown -> {
                _uiState.update {
                    it.copy(isEditingReminder = false)
                }
            }

            AgendaItemAction.ShowTimePicker -> {
                _uiState.update {
                    it.copy(isEditingTime = true)
                }
            }

            AgendaItemAction.HideTimePicker -> {
                _uiState.update {
                    it.copy(isEditingTime = false)
                }
            }

            AgendaItemAction.CloseEditDescriptionScreen -> {
                _uiState.update {
                    it.copy(isEditingItem = false)
                }
            }

            AgendaItemAction.CloseEditTitleScreen -> {
                _uiState.update {
                    it.copy(isEditingItem = false)
                }
            }

            AgendaItemAction.LaunchEditDescriptionScreen -> {
                _uiState.update {
                    it.copy(isEditingItem = true)
                }
            }

            AgendaItemAction.LaunchEditTitleScreen -> {
                _uiState.update {
                    it.copy(isEditingItem = true)
                }
            }

            AgendaItemAction.CancelEdit -> {
                _uiState.update {
                    it.copy(
                        isEditingItem = false,
                        isEditingDate = false,
                        isEditingTime = false,
                        isEditingReminder = false
                    )
                }
            }

            AgendaItemAction.LaunchDateTimeEditScreen -> {
                _uiState.update {
                    it.copy(isEditingItem = true)
                }
            }

            AgendaItemAction.CloseDetailScreen -> {
                Unit
                // go back to Agenda screen
            }

            AgendaItemAction.SaveDateTimeEdit -> {
                _uiState.update {
                    it.copy(isEditingItem = false)
                }
            }
        }
    }
}
