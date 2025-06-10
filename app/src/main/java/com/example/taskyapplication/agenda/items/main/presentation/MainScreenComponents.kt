package com.example.taskyapplication.agenda.items.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.domain.AgendaScreenCalendarList
import com.example.taskyapplication.agenda.domain.buildAgendaScreenCalendar
import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import com.example.taskyapplication.agenda.items.main.data.AgendaSummary
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun AgendaSummary(
    modifier: Modifier = Modifier,
    dateHeading: String,
    dailySummary: List<AgendaSummary>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier,
            text = dateHeading,
            style = TaskyTypography.headlineLarge.copy(
                color = taskyColors.primary,
                fontSize = 24.sp
            )
        )
        LazyColumn {
            items(dailySummary) { item ->
                AgendaItemCard(
                    modifier = Modifier,
                    agendaItemType = item.type,
                    title = item.title,
                    description = item.description,
                    time = item.startTime,
                    date = item.startDate
                )
            }
        }

    }

}

@Composable
fun AgendaScreenScrollableDates(
    modifier: Modifier = Modifier,
    dateList: List<AgendaScreenCalendarList> = buildAgendaScreenCalendar(),
    onSelectDate: (String) -> Unit = {},
) {
    val initialIndex = 15
    val lazyGridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = initialIndex
    )

    LazyHorizontalGrid(
        modifier = modifier.height(130.dp),
        state = lazyGridState,
        rows = GridCells.Fixed(1),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(dateList) { item ->
            AgendaScreenCalendarDateItem(
                dayOfWeekInitial = item.dayOfWeek[0],
                dateOfMonth = item.dayOfMonth,
                isSelected = false
            )
        }
    }
}

@Composable
fun AgendaScreenCalendarDateItem(
    modifier: Modifier = Modifier,
    dayOfWeekInitial: Char,
    dateOfMonth: String,
    isSelected: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(
                color = taskyColors.onPrimary,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = dayOfWeekInitial.toString(),
            style = TaskyTypography.labelMedium.copy(
                color = taskyColors.primary
            )
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = dateOfMonth,
            style = TaskyTypography.labelMedium.copy(
                color = taskyColors.primary
            )
        )

    }

}

@Composable
fun AgendaItemCard(
    modifier: Modifier = Modifier,
    agendaItemType: AgendaItemType = AgendaItemType.EVENT,
    title: String = "Title",
    description: String = "item description",
    time: String = "10:00",
    date: String = "May 7",
    launchPopupMenu: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = agendaItemType.color),
            contentColor = taskyColors.primary,
            disabledContainerColor = colorResource(id = agendaItemType.color),
            disabledContentColor = colorResource(id = agendaItemType.color)
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // heading
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = TaskyTypography.headlineLarge.copy(
                            color = taskyColors.primary
                        )
                    )
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "menu options",
                        modifier = Modifier.clickable { launchPopupMenu() }
                    )
                } // end of title row
                Text(
                    text = description,
                    style = TaskyTypography.bodyMedium.copy(
                        color = taskyColors.primary
                    )
                )
                // date and time
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$date, ",
                        style = TaskyTypography.bodyMedium.copy(
                            color = taskyColors.primary
                        )
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = time,
                        style = TaskyTypography.bodyMedium.copy(
                            color = taskyColors.primary
                        )
                    )
                }
            }
        }
    )
}

@Composable
fun MainScreenHeader(
    modifier: Modifier = Modifier,
    launchDatePicker: () -> Unit = {},
    launchLogoutDropDown: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    showLogoutDropDown: Boolean = false,
    userInitials: String = "AB"
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .padding(top = 16.dp, bottom = 16.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left row with date and dropdown
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Month".uppercase(),
                style = TaskyTypography.labelMedium.copy(
                    color = taskyColors.onPrimary
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.dropdown),
                tint = taskyColors.onPrimary,
                contentDescription = "select month",
                modifier = Modifier
                    .scale(1.2f)
                    .padding(start = 4.dp)
                    .clickable { launchDatePicker() }
            )
        }
        // right row with calendar and initials
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                tint = taskyColors.onPrimary,
                contentDescription = "calendar",
                modifier = Modifier
                    .scale(1.2f)
                    .padding(start = 4.dp)
                    .clickable { launchDatePicker() }
            )
            Box(
                modifier = Modifier
            ) {
                Text(
                    text = userInitials,
                    modifier = Modifier
                        .clickable { launchLogoutDropDown() }
                        .drawBehind {
                            drawRoundRect(
                                color = Color(0xFF76808F),
                                cornerRadius = CornerRadius(40.dp.toPx())
                            )
                        }
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    style = TaskyTypography.labelMedium.copy(
                        color = taskyColors.inputFieldGray
                    )
                )
                MainScreenLogoutMenu(
                    modifier = Modifier,
                    onLogoutClick = onLogoutClick,
                    onDismissRequest = onDismissRequest,
                    isExpanded = showLogoutDropDown
                )
            }
        }
    }
}

@Composable
fun MainScreenFab(
    modifier: Modifier = Modifier,
    onClickFab: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = modifier
            .padding(16.dp),
        onClick = onClickFab,
        containerColor = taskyColors.primary,
        contentColor = taskyColors.onPrimary,
        shape = RoundedCornerShape(12.dp),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp
        ),
        content = {
            Icon(
                modifier = Modifier
                    .padding(24.dp)
                    .scale(1.2f),
                imageVector = Icons.Default.Add,
                contentDescription = "add new item"
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenFabPreview() {
    MainScreenFab()
}

@Preview(showBackground = true)
@Composable
fun MainScreenHeaderPreview() {
    MainScreenHeader()
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    AgendaItemCard()
}

@Preview(showBackground = true)
@Composable
fun CalendarItemPreview() {
    AgendaScreenCalendarDateItem(
        dayOfWeekInitial = 'M',
        dateOfMonth = "15",
        isSelected = true,
    )
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    AgendaScreenScrollableDates()
}