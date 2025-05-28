package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// time picker
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyTimePicker(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    state: TimePickerState,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimePickerDialog(
            onDismiss = onDismiss,
            onConfirm = onConfirm,
            content = {
                TimePicker(
                    state = state,
                )
            }
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }
            ) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

// date picker
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyDatePicker(
    modifier: Modifier = Modifier,
    state: DatePickerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DateDialog(
            onDismiss = onDismiss,
            onConfirm = onConfirm,
            content = {
                DatePicker(
                    modifier = Modifier.padding(16.dp),
                    state = state,
                    headline = { Text(text = "Select Task Date") }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }
            ) {
                Text("OK")
            }
        },
        content = { content() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AgendaTimePickerPreview() {
    TaskyTimePicker(
        state = TimePickerState(
            initialHour = 12,
            initialMinute = 0,
            is24Hour = false
        ),
        onDismiss = {},
        onConfirm = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AgendaDatePickerPreview() {
    TaskyDatePicker(
        state = rememberDatePickerState(),
        onDismiss = {},
        onConfirm = {},
    )
}
