package com.example.taskyapplication.agenda.items.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.data.model.VisitorStatus
import com.example.taskyapplication.agenda.domain.toInitials
import com.example.taskyapplication.agenda.items.event.data.Attendee
import com.example.taskyapplication.agenda.items.event.data.AttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.VerifyAttendeeResponse
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

val chipList = listOf(
    VisitorStatus.ALL,
    VisitorStatus.GOING,
    VisitorStatus.NOT_GOING,
)

@Composable
fun VisitorGroup(
    modifier: Modifier = Modifier,
    visitorList: List<Attendee> = emptyList(),
    userEmail: String,
    isEditingScreen: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VisitorHeader(
            modifier = Modifier,
            isEditingScreen = isEditingScreen,
            userEmail = userEmail
        )
        VisitorChips()
        if (visitorList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
            ) {
                items(visitorList) { attendee ->
                    VisitorNameRowEdit(
                        modifier = Modifier,
                        onSelectDelete = { },
                        visitorName = attendee.fullName
                    )
                }
            }
        }
    }
}

@Composable
fun VisitorChips(
    modifier: Modifier = Modifier,
    defaultSelectedItemIndex: Int = 0,
    sortVisitorList: (String) -> Unit = {},
    isSelected: Boolean = false
) {
    var selectedIndex by rememberSaveable { mutableStateOf(defaultSelectedItemIndex) }
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        userScrollEnabled = true,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        itemsIndexed(chipList) { index, status ->
            FilterChip(
                modifier = Modifier,
                selected = chipList[selectedIndex] == chipList[index],
                onClick = {
                    selectedIndex = index
                    sortVisitorList(status.value)
                },
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = Color.Transparent,
                    selectedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    borderWidth = 1.dp,
                    selectedBorderWidth = 1.dp
                ),
                shape = RoundedCornerShape(40.dp),

                colors = FilterChipDefaults.filterChipColors(
                    containerColor = if (isSelected) taskyColors.primary else taskyColors.surfaceContainerHigh,
                    labelColor = if (isSelected) taskyColors.onPrimary else taskyColors.onSurface,
                    selectedContainerColor = taskyColors.primary,
                ),
                label = {
                    Text(
                        text = chipList[index].value,
                        style = TaskyTypography.headlineSmall.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun VisitorNameRowEdit(
    modifier: Modifier = Modifier,
    onSelectDelete: (attendeeId: String) -> Unit = {},
    isEditingScreen: Boolean = false,
    isMeetingHost: Boolean = false,
    visitorName: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(8.dp))
            .background(color = taskyColors.surface)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text( // Visitor Initials
            text = "BB",
                //visitorName.toInitials(),
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xFF76808F),
                        cornerRadius = CornerRadius(40.dp.toPx())
                    )
                }
                .padding(horizontal = 12.dp, vertical = 12.dp),
            style = TaskyTypography.headlineSmall.copy(
                color = taskyColors.onPrimary
            )
        )
        Text( // Visitor Name
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(8f),
            text = visitorName,
            style = TaskyTypography.bodyMedium.copy(
                color = taskyColors.onSurface
            )
        )
        if (isEditingScreen) {
            Icon( // delete icon
                imageVector = Icons.Default.Delete,
                tint = taskyColors.error,
                contentDescription = "Arrow Forward",
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelectDelete }
            )
        }
    }
}

@Composable
fun VisitorNameRowHost(
    modifier: Modifier = Modifier,
    onSelectDelete: (attendeeId: String) -> Unit = {},
    isMeetingHost: Boolean = false,
    visitorName: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(8.dp))
            .background(color = taskyColors.surface)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text( // Visitor Initials
            text = "BB",
                //visitorName.toInitials(),
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xFF76808F),
                        cornerRadius = CornerRadius(40.dp.toPx())
                    )
                }
                .padding(horizontal = 12.dp, vertical = 12.dp),
            style = TaskyTypography.headlineSmall.copy(
                color = taskyColors.onPrimary
            )
        )
        Text( // Visitor Name
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(8f),
            text = visitorName,
            style = TaskyTypography.bodyMedium.copy(
                color = taskyColors.onSurface
            )
        )
        if (isMeetingHost) {
            Text(
                modifier = Modifier
                    .alpha(0.7f),
                text = stringResource(R.string.event_host).uppercase(),
                style = TaskyTypography.labelSmall.copy(
                    color = taskyColors.onSurfaceVariant
                )
            )
        }
    }
}


@Composable
fun VisitorHeader(
    modifier: Modifier = Modifier,
    showAddVisitorBottomSheet: () -> Unit = {},
    onAddNewVisitor: (String) -> Unit = {},
    onCancelAddingVisitor: () -> Unit = {},
    userEmail: String = "",
    visitorList: List<String> = emptyList(),
    isBottomSheetEnabled: Boolean = false,
    isLoading: Boolean = false,
    isEditingScreen: Boolean = false,
    isValidEmail: Boolean = false
) {
    Column() {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Visitors",
                style = TaskyTypography.headlineMedium.copy(
                    color = taskyColors.primary
                )
            )
            if (isEditingScreen) {
                Box(
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = taskyColors.onSurface,
                        contentDescription = "Add Visitor",
                        modifier = Modifier
                            .clickable { showAddVisitorBottomSheet() }
                            .drawBehind {
                                drawRoundRect(
                                    color = Color(0xFFF2F3F7),
                                    cornerRadius = CornerRadius(3.dp.toPx())
                                )
                            }
                            .padding(vertical = 12.dp, horizontal = 12.dp)
                    )
                    if (isBottomSheetEnabled) {
                        AddAttendeeBottomSheet(
                            modifier = Modifier,
                            isLoading = false,
                            userEmail = userEmail,
                            isButtonEnabled = isLoading,
                            isValidEmail = isValidEmail,
                            onAddAttendee = { onAddNewVisitor(userEmail) },
                            onCancelAddAttendee = { onCancelAddingVisitor() }
                        )
                    }
                }
            }
        } // end of heading row
        LazyColumn {
            items(visitorList) { attendee ->
                VisitorNameRowHost(
                    modifier = Modifier,
                    visitorName = attendee
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VisitorGroupPreview() {
    VisitorGroup(
        userEmail = "john.mclean@examplepetstore.com"
    )
}

//val responseList = listOf(
//    VerifyAttendeeResponse(email = "emma@sassy.com", fullName = "Jane Smith", userId = "12"),
//    VerifyAttendeeResponse(email = "emma@sassy.com", fullName = "Emily Brown", userId = "12"),
//    VerifyAttendeeResponse(email = "emma@sassy.com", fullName = "Natasha Jones", userId = "12"),
//    VerifyAttendeeResponse(email = "emma@sassy.com", fullName = "Malia James", userId = "12"),
//)


val responseList = listOf(
    "Jane Smith",
     "Emily Brown",
    "Natasha Jones",
    "Malia James"
)

@Preview(showBackground = true)
@Composable
fun VisitorHeaderPreview() {
    VisitorHeader(
        visitorList = responseList,
        userEmail = "charlesgdawe@examplepetstore.com"
    )
}

@Preview(showBackground = true)
@Composable
fun VisitorChipsPreview() {
    VisitorChips()
}

@Preview(showBackground = true)
@Composable
fun VisitorEditPreview() {
    VisitorNameRowEdit(
        visitorName = "John Doe",
        isEditingScreen = true,
    )
}

@Preview(showBackground = true)
@Composable
fun VisitorHostPreview() {
    VisitorNameRowHost(
        visitorName = "John Doe",
        isMeetingHost = true,
    )
}