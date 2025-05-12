package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

// used for title and type of agenda item
@Composable
fun AgendaIconTextRow(
    modifier: Modifier = Modifier,
    itemIcon: @Composable () -> Unit,
    textItem: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemIcon()
        textItem()
    }
}

// description
@Composable
fun AgendaDescriptionText(
    modifier: Modifier = Modifier,
    agendaItemDescription: String = "",
    isEditing: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            text = agendaItemDescription,
            textAlign = TextAlign.Left,
            overflow = TextOverflow.Ellipsis,
            style = TaskyTypography.bodyMedium,
            color = taskyColors.primary
        )
        if (isEditing) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp),
                painter = painterResource(R.drawable.chevron_right),
                tint = taskyColors.primary,
                contentDescription = ""
            )
        }
    }
}

// title
@Composable
fun AgendaTitleRow(
    modifier: Modifier = Modifier,
    agendaItemTitle: String,
    isEditing: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AgendaIconTextRow(
            itemIcon = {
                Icon(
                    painter = painterResource(R.drawable.agenda_title_icon),
                    tint = taskyColors.primary,
                    contentDescription = ""
                )
            },
            textItem = {
                Text(
                    modifier = Modifier.padding(start = 12.dp, end = 16.dp),
                    text = agendaItemTitle,
                    style = TaskyTypography.headlineLarge,
                    color = taskyColors.onSurface
                )
            }
        )
        if (isEditing) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp),
                painter = painterResource(R.drawable.chevron_right),
                tint = taskyColors.primary,
                contentDescription = ""
            )
        }

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

// previews
@Preview(showBackground = true)
@Composable
fun AgendaDescriptionPreview() {
    AgendaDescriptionText(
        isEditing = true,
        agendaItemDescription = "This is a sample description for the agenda item. It can be quite long and should be truncated if it exceeds the available space."
    )
}


@Preview(showBackground = true)
@Composable
fun AgendaItemTypePreview() {
    AgendaIconTextRow(
        itemIcon = {
            Icon(
                imageVector = Icons.Outlined.CheckCircle, // Or another square icon
                contentDescription = "Home",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .height(24.dp)
                    .width(24.dp),
                tint = Color.Blue
            )
        },
        textItem = {
            Text(
                text = "Reminder".uppercase(),
                style = TaskyTypography.labelMedium,
                color = taskyColors.onSurface
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun AgendaItemTitlePreview() {
    AgendaTitleRow(
        agendaItemTitle = "This is a sample title.",
        isEditing = true
    )
}
