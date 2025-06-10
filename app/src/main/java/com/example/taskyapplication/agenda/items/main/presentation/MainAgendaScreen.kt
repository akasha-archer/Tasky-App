package com.example.taskyapplication.agenda.items.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.items.main.data.AgendaScreenUi

@Composable
fun AgendaMainScreen(
    modifier: Modifier = Modifier,
    agendaScreenUi: AgendaScreenUi = AgendaScreenUi()
) {
    TaskyBaseScreen(
        modifier = modifier,
        screenHeader = {
            MainScreenHeader()
        },
        mainContent = {
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
                MainScreenFab(
                    launchPopupMenu = { },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 24.dp)
                )
            } // end of box
        }
    )
}

@Preview
@Composable
fun AgendaMainScreenPreview() {
    AgendaMainScreen()
}