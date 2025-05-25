package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography
import com.example.taskyapplication.R

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
    isEditing: Boolean = false,
    onClickEdit: () -> Unit = {},
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
            style = TaskyTypography.bodyMedium
                .copy(color = taskyColors.primary),
        )
        if (isEditing) {
            Icon(
                modifier = Modifier
                    .clickable { onClickEdit() }
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
    isEditing: Boolean = false,
    onClickEdit: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AgendaIconTextRow(
            itemIcon = {
                if (agendaItemTitle.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.agenda_title_icon),
                        tint = taskyColors.primary,
                        contentDescription = ""
                    )
                }
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
                    .clickable { onClickEdit() }
                    .padding(end = 8.dp),
                painter = painterResource(R.drawable.chevron_right),
                tint = taskyColors.primary,
                contentDescription = ""
            )
        }
    }
}
