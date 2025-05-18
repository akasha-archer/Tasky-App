package com.example.taskyapplication.agenda.presentation.reminder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskyapplication.agenda.presentation.AgendaEnums
import com.example.taskyapplication.agenda.presentation.components.BaseDetailScreen
import com.example.taskyapplication.main.components.TaskyScaffold

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderDetailRoot(
    modifier: Modifier = Modifier
) {
    TaskyScaffold(
        mainContent = {
            ReminderDetail(modifier)
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderDetail(
    modifier: Modifier = Modifier
) {
    BaseDetailScreen(
        modifier = modifier,
        agendaItemType = AgendaEnums.REMINDER.name,
        agendaItemTitle = "Reminder Title",
        agendaItemDescription = "Reminder Description",
        taskTime = "10:00 AM",
        taskDate = "2023-10-15",
        taskReminderTime = "09:45 AM",
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ReminderScreenPreview() {
    ReminderDetailRoot()
}
