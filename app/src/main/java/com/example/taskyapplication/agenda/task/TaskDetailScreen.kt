package com.example.taskyapplication.agenda.task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskyapplication.agenda.presentation.AgendaItemType
import com.example.taskyapplication.agenda.presentation.components.BaseDetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    taskDescription: String,
    taskTitle: String,
    onClickEdit: () -> Unit = {},
) {
    BaseDetailScreen(
        modifier = modifier,
        agendaItemType = AgendaItemType.TASK.name,
        agendaItemTitle = taskTitle,
        agendaItemDescription = taskDescription,
        onClickEdit = onClickEdit
    )
}


@Composable
fun TaskEditScreen(
modifier: Modifier = Modifier,
) {
    // navigation: pop back to Detail screen
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    TaskDetailScreen(
        taskDescription = "Task Description",
        taskTitle = "Task Title",
    )
}