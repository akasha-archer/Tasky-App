package com.example.taskyapplication.agenda.items.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.main.data.AgendaScreenUi

@Composable
fun AgendaMainRoot(
    modifier: Modifier = Modifier,
    launchNewEventScreen: () -> Unit = {},
    launchNewReminderScreen: () -> Unit = {},
    launchNewTaskScreen: () -> Unit = {},
    agendaScreenUi: AgendaScreenUi = AgendaScreenUi()
) {
    AgendaMainScreen(
        modifier = modifier,
        createNewItem = { type ->
            when (type) {
                AgendaItemType.EVENT -> launchNewEventScreen()
                AgendaItemType.REMINDER -> launchNewReminderScreen()
                AgendaItemType.TASK -> launchNewTaskScreen()
            }
        },
        agendaScreenUi = agendaScreenUi
    )
}

@Composable
fun AgendaMainScreen(
    modifier: Modifier = Modifier,
    createNewItem: (AgendaItemType) -> Unit = {},
    agendaScreenUi: AgendaScreenUi = AgendaScreenUi()
) {
    TaskyBaseScreen(
        modifier = modifier,
        screenHeader = {
            MainScreenHeader()
        },
        mainContent = {
            var showFabPopup by rememberSaveable { mutableStateOf(false) }
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                ) {
                    AgendaScreenScrollableDates()
                    AgendaSummary(
                        modifier = Modifier,
                        agendaScreenUi = AgendaScreenUi()
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                ) {
                    MainScreenFab(
                        onClickFab = { showFabPopup = true },
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 24.dp)
                    )
                    FabPopupMenu(
                        modifier = Modifier,
                        onDismissRequest = { showFabPopup = false },
                        navigateToScreen = { createNewItem(it) },
                        isExpanded = showFabPopup
                    )
                } // end of Fab popup Box
            } // end of outer Box
        }
    )
}

@Preview
@Composable
fun AgendaMainScreenPreview() {
    AgendaMainScreen()
}