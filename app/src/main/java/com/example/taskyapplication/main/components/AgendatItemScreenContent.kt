package com.example.taskyapplication.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    agendaItemType: @Composable () -> Unit,
    agendaItemTitle: @Composable () -> Unit,
    agendaItemDescription: (@Composable () -> Unit)? = null,
    eventMedia: (@Composable () -> Unit)? = null,
    agendaItemStartTime:@Composable () -> Unit,
    agendaItemEndTime: (@Composable () -> Unit)? = null,
    agendaItemReminderTime: @Composable () -> Unit,
    agendaItemDeleteButton: (@Composable () -> Unit)? = null
) {
    Column() {
        agendaItemType()
        Spacer(Modifier.height(24.dp))
        agendaItemTitle()
        // divider below title
        Spacer(Modifier.height(24.dp))
        if (agendaItemDescription != null) {
            agendaItemDescription()
        }
        if (eventMedia == null) {
            // divider -- if media is null (below description)
        } else {
            eventMedia()
        }
        agendaItemStartTime()
        if (agendaItemEndTime != null) {
            // divider - above end time
            agendaItemEndTime()
        }
        // divider -- above reminder time
        agendaItemReminderTime()


        // space
        if (agendaItemDeleteButton != null) {
            // divider - bottom of screen
            agendaItemDeleteButton()
        }
    }
}

// delete button
@Composable
fun AgendaItemDeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
            color = taskyColors.surfaceContainerHigh,
            thickness = 1.dp,
        )
        TextButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Delete Reminder".uppercase(),
                style = TaskyTypography.labelSmall,
                color = taskyColors.error
            )
        }
    }
}

@Composable
fun AgendaHeadingItem(
    modifier: Modifier = Modifier,
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
           text = "Reminder".uppercase(),
           style = TaskyTypography.labelMedium,
           color = taskyColors.onSurface
       )
   }
}

@Preview(showBackground = true)
@Composable
fun AgendaTypePreview() {
    AgendaHeadingItem()
}

@Preview(showBackground = true)
@Composable
fun DeleteButtonPreview() {
    AgendaItemDeleteButton(onClick = {})
}
