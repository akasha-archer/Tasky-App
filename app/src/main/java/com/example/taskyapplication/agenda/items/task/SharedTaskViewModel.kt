package com.example.taskyapplication.agenda.items.task

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.common.AgendaItemEvent
import com.example.taskyapplication.agenda.items.task.data.mappers.asTaskNetworkModel
import com.example.taskyapplication.agenda.items.task.data.mappers.asTaskUi
import com.example.taskyapplication.agenda.items.task.data.mappers.asUpdateTaskNetworkModel
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.agenda.items.task.presentation.TaskUiState
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
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: String? = savedStateHandle.get<String>("taskId")

    var count = 1

    init {
        Log.i("SharedTaskViewModel", "SharedTaskViewModel created with count: ${count++}")

        if (taskId != null) {
            loadExistingTask(taskId)
        }
    }

    private val agendaEventChannel = Channel<AgendaItemEvent>()
    val agendaEvents = agendaEventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = TaskUiState(),
        )

    private fun loadExistingTask(taskId: String) {
        viewModelScope.launch {
            val requestedTask = repository.getTaskById(taskId)
            if (requestedTask != null) {
                _uiState.value = requestedTask.asTaskUi()
            }
        }
    }

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

            val result = if (isNewTask(currentTaskId)) {
                repository.createNewTask(
                    taskToCreateOrUpdate.asTaskNetworkModel()
                )
            }
            else {
                repository.updateTask(
                    taskToCreateOrUpdate.asUpdateTaskNetworkModel()
                )
            }

            _uiState.update {
                it.copy(isEditingItem = false)
            }

            when {
                result.isFailure -> {
                    agendaEventChannel.send(
                        AgendaItemEvent.DeleteError(
                            errorMessage = "Item not deleted. Please try again later."
                        )
                    )
                }

                result.isSuccess -> {
                    agendaEventChannel.send(AgendaItemEvent.DeleteSuccess)
                }
            }
        }
    }


    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDeletingItem = true) }
            val result = repository.deleteTask(taskId)
            _uiState.update { it.copy(isDeletingItem = false) }

            when {
                result.isFailure -> {
                    agendaEventChannel.send(
                        AgendaItemEvent.DeleteError(
                            errorMessage = "Task not deleted. Please try again later."
                        )
                    )
                }
                result.isSuccess -> {
                    agendaEventChannel.send(AgendaItemEvent.DeleteSuccess)
                }
            }
        }
    }

    fun executeActions(action: AgendaItemAction) {
        when (action) {
            AgendaItemAction.SaveAgendaItemUpdates -> createOrUpdateTask()
            is AgendaItemAction.OpenExistingTask -> {
                loadExistingTask(action.id)
            }

            is AgendaItemAction.EditExistingTask -> {
                loadExistingTask(action.id)
            }

            is AgendaItemAction.OpenExistingReminder -> {
                Unit
            }

            is AgendaItemAction.EditExistingReminder -> {
                Unit
            }

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
