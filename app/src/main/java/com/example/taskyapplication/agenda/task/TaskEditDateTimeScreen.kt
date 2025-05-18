package com.example.taskyapplication.agenda.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.agenda.presentation.AgendaEnums
import com.example.taskyapplication.agenda.presentation.ReminderOptions
import com.example.taskyapplication.agenda.presentation.components.BaseEditDateTimeScreen
import com.example.taskyapplication.agenda.presentation.components.TaskyDatePicker
import com.example.taskyapplication.agenda.presentation.components.TaskyTimePicker
import com.example.taskyapplication.domain.utils.timeAsLong
import com.example.taskyapplication.domain.utils.toDateAsString
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditDateTimeScreen(
    modifier: Modifier = Modifier,
    onSelectTitleEdit: () -> Unit = {}, // navigation
    onSelectDescriptionEdit: () -> Unit = {}, //navigation
    onSave: (date:String, time:String, reminder:String) -> Unit,
    onCancel: () -> Unit = {},
    viewModel: TaskViewModel = hiltViewModel()
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    var selectedDate by rememberSaveable {
        mutableStateOf(
            LocalDate.now().format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            )
        )
    }
    var selectedTime by rememberSaveable {
        mutableStateOf(
            LocalTime.now().format(
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            )
        )
    }
    var selectedReminder by rememberSaveable {
        mutableStateOf(ReminderOptions.THIRTY_MINUTES_BEFORE.value)
    }

    val taskState by viewModel.taskState.collectAsStateWithLifecycle()

    BaseEditDateTimeScreen(
        modifier = modifier,
        agendaItemType = AgendaEnums.TASK.name,
        agendaItemTitle = taskState.taskTitle,
        agendaItemDescription = taskState.taskDescription,
        onSelectTitleEdit = {
            onSelectTitleEdit()
        },
        onSelectDescriptionEdit = {
            onSelectDescriptionEdit()
        },
        onSelectEditDate = {
            showDatePicker = true
        },
        onSelectEditTime = {
            showTimePicker = true
        },
        onClickSave = {
            selectedDate = datePickerState.selectedDateMillis?.toDateAsString() ?: LocalDate.now().toEpochDay().toDateAsString()
            selectedTime = timePickerState.hour.toString() + ":" + timePickerState.minute.toString()
            viewModel.updateTaskDateTime(
                newDate = datePickerState.selectedDateMillis ?: LocalDate.now().toEpochDay(),
                newTime = selectedTime.timeAsLong()
            )
            viewModel.updateTaskReminderTime(selectedReminder)
            onSave(selectedDate, selectedTime, selectedReminder)
        },
        onClickCancel = { onCancel() },
        onSelectReminderTime = {
            // show reminder drop down menu
        },
        onSelectDelete = {},
        launchDatePicker = {
            if (showDatePicker) {
                TaskyDatePicker(
                    state = datePickerState,
                    onDismiss = { showDatePicker = false },
                    onConfirm = {
                        showDatePicker = false
                        selectedDate = datePickerState.selectedDateMillis?.toDateAsString()

                    }
                )
            }
        },
        launchTimePicker = {
            if (showTimePicker) {
                TaskyTimePicker(
                    state = timePickerState,
                    onDismiss = { showTimePicker = false },
                    onConfirm = {
                        showTimePicker = false
                        selectedTime = timePickerState.hour.toString() + ":" + timePickerState.minute.toString()
                    }
                )
            }
        },
        selectedDate = selectedDate,
        selectedTime = selectedTime
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EditDateTimeScreenPreview() {
    EditDateTimeScreen(
        onSave = { _, _, _ -> },
    )
}