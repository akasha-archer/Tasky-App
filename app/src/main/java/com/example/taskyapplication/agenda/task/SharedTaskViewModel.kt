package com.example.taskyapplication.agenda.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.task.data.mappers.asTaskNetworkModel
import com.example.taskyapplication.agenda.task.domain.TaskRepository
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
class SharedTaskViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = TaskUiState(),
    )

    fun onTaskAction(action: AgendaItemAction) {
        when(action) {
           AgendaItemAction.SaveTaskUpdates -> {
               val newTitle = _uiState.value.title
               val newDescription = _uiState.value.description
               val taskTime = _uiState.value.time
               val taskDate = _uiState.value.date
               val reminderTime = _uiState.value.remindAt

               if (newTitle.isBlank() || newDescription.isBlank()) {
                   return
               }
               val newTaskId: String = UUID.randomUUID().toString()
               _uiState.update {
                   it.copy(
                       id = newTaskId
                   )
               }
               val taskUiState = TaskUiState(
                   id = newTaskId,
                   title = newTitle,
                   description = newDescription,
                   time = taskTime,
                   date = taskDate,
                   remindAt = reminderTime
               )
               viewModelScope.launch {
                   repository.createNewTask(
                       taskUiState.asTaskNetworkModel()
                   )
               }
               _uiState.update {
                   it.copy(isEditingItem = false)
               }
            }
            is AgendaItemAction.SetTitle -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(title = action.title) }
                }
            }
            is AgendaItemAction.SetDate -> {
                _uiState.update { it.copy(date = action.date) }
            }
            is AgendaItemAction.SetDescription -> {
                _uiState.update { it.copy(description = action.description) }
            }
            is AgendaItemAction.SetReminderTime -> {
                _uiState.update { it.copy(remindAt = action.reminder) }
            }
            is AgendaItemAction.SetTime -> {
                _uiState.update { it.copy(time = action.time) }
            }
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
                    it.copy(isEditingItem = true) }
            }
            AgendaItemAction.HideReminderDropDown -> {
                _uiState.update {
                    it.copy(isEditingItem = false)
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
            is AgendaItemAction.LaunchEditTitleScreen -> {
                _uiState.update {
                    it.copy(isEditingItem = true)
                }
            }
        }
    }
}
