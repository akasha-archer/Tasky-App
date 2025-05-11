package com.example.taskyapplication.agenda.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Template for all screens
@Composable
fun AgendaItem(
    agendaItemType: @Composable () -> Unit,
    agendaItemTitle: @Composable () -> Unit,
    agendaItemDescription: (@Composable () -> Unit)? = null,
    eventMedia: (@Composable () -> Unit)? = null,
    agendaItemStartTime:@Composable () -> Unit,
    agendaItemEndTime: (@Composable () -> Unit)? = null,
    agendaItemReminderTime: @Composable () -> Unit,
) {
    Column {
        agendaItemType()
        Spacer(Modifier.height(24.dp))
        agendaItemTitle()
        Spacer(Modifier.height(20.dp))
        AgendaScreenDivider()

        if (agendaItemDescription != null) {
            Spacer(Modifier.height(20.dp))
            agendaItemDescription()
        }
        if (eventMedia == null) {
            AgendaScreenDivider()
        } else {
            eventMedia()
        }
        agendaItemStartTime()
        if (agendaItemEndTime != null) {
            AgendaScreenDivider()
            agendaItemEndTime()
        }
        AgendaScreenDivider()
        Spacer(Modifier.height(20.dp))
        agendaItemReminderTime()
        Spacer(Modifier.height(20.dp))
        AgendaScreenDivider()
    }
}
