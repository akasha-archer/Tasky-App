package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

// Template for all screens
@Composable
fun AgendaItem(
    modifier: Modifier = Modifier,
    agendaItemType: @Composable () -> Unit,
    agendaItemTitle: @Composable () -> Unit,
    agendaItemDescription: (@Composable () -> Unit)? = null,
    eventMedia: (@Composable () -> Unit)? = null,
    agendaItemStartTime:@Composable () -> Unit,
    agendaItemEndTime: (@Composable () -> Unit)? = null,
    agendaItemReminderTime: @Composable () -> Unit,
    launchDatePicker: (@Composable () -> Unit)? = null,
    launchTimePicker: (@Composable () -> Unit)? = null,
    launchReminderDropDown: (@Composable () -> Unit)? = null,
    eventVisitorSection: (@Composable () -> Unit)? = null,
//    agendaItemDeleteButton: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        agendaItemType()
        Spacer(Modifier.height(24.dp))
        agendaItemTitle()
        Spacer(Modifier.height(20.dp))
        AgendaScreenDivider()

        if (agendaItemDescription != null) {
            Spacer(Modifier.height(20.dp))
            agendaItemDescription()
            Spacer(Modifier.height(20.dp))
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
        if (eventVisitorSection != null) {
            eventVisitorSection()
        }

//        agendaItemDeleteButton()
    }
    if (launchDatePicker != null) {
        launchDatePicker()
    }
    if (launchTimePicker != null) {
        launchTimePicker()
    }
    if (launchReminderDropDown != null) {
        launchReminderDropDown()
    }
}

@Composable
fun AgendaScreenDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier,
        color = taskyColors.surfaceContainerHigh,
        thickness = 1.dp,
    )
}

@Composable
fun AgendaHeadingItem(
    modifier: Modifier = Modifier,
    agendaItemType: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.CheckCircle, // Or another square icon
            contentDescription = "Home",
            modifier = Modifier
                .padding(end = 12.dp)
                .height(24.dp)
                .width(24.dp),
            tint = Color.Blue
        )
        Text(
            text = agendaItemType.uppercase(),
            style = TaskyTypography.labelMedium,
            color = taskyColors.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AgendaTypePreview() {
    AgendaHeadingItem(
        agendaItemType = "Reminder"
    )
}
