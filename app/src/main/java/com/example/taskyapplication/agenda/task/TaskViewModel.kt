package com.example.taskyapplication.agenda.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.data.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {

    private val _taskState = MutableStateFlow(TaskState())
    val taskState = _taskState.asStateFlow()

    fun updateTitleText(newTitle: String) {
        viewModelScope.launch {
            _taskState.value = _taskState.value.copy(
                taskTitle = newTitle
            )
        }
    }

    fun updateDescriptionText(newDescription: String) {
        viewModelScope.launch {
            _taskState.value = _taskState.value.copy(
                taskDescription = newDescription
            )
        }
    }

    fun updateTaskDateTime(newDate: Long, newTime: Long) {
        viewModelScope.launch {
            _taskState.value = _taskState.value.copy(
                taskStartTime = newTime,
                taskStartDate = newDate
            )
        }
    }

    fun updateTaskReminderTime(selectedReminder: String) {
        viewModelScope.launch {
            _taskState.value = _taskState.value.copy(
                taskReminderTime = selectedReminder
            )
        }
    }
}
