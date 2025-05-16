package com.example.taskyapplication.agenda.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.data.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(

) : ViewModel() {

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

    fun updateTaskStartTime(newStartTime: Long) {
        viewModelScope.launch {
            val taskTime =
                LocalTime.ofInstant(Instant.ofEpochMilli(newStartTime), ZoneId.systemDefault())
            val formattedTime =
                taskTime.format(java.time.format.DateTimeFormatter.ofPattern("h:mm a"))
            _taskState.value = _taskState.value.copy(
                taskStartTime = formattedTime
            )
        }
    }

    fun updateTaskDate(newDate: Long) {
        viewModelScope.launch {
            val taskDate =
                LocalDate.ofInstant(Instant.ofEpochMilli(newDate), ZoneId.systemDefault())
            val formattedDate =
                taskDate.format(java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy"))
            _taskState.value = _taskState.value.copy(
                taskStartDate = formattedDate
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

    /*
    *    val currMoment = LocalDateTime.now()
                val dateTimeAsLong = currMoment.toInstant(ZoneOffset.UTC).toEpochMilli()
               val backToDateTime =
                   LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTimeAsLong), ZoneId.systemDefault())

                val extractDate = backToDateTime.toLocalDate().format(java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                val extractTime = backToDateTime.toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("h:mm a"))

    *
    * */


}