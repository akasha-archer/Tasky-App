package com.example.taskyapplication.agenda.items.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun MainScreenHeader(
    modifier: Modifier = Modifier,
    launchDatePicker: () -> Unit = {},
    launchLogoutDropDown: () -> Unit = {},
    userInitials: String = "AB"
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .padding(top = 16.dp, bottom = 16.dp)
            .padding(horizontal = 16.dp),
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
        }


    }
}

/*
* @Composable
fun AgendaItemScreenHeader(
    modifier: Modifier = Modifier,
    firstItem: @Composable RowScope.() -> Unit,
    secondItem: @Composable RowScope.() -> Unit,
    thirdItem: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 4.dp)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        firstItem()
        secondItem()
        thirdItem()
    }
    Spacer(modifier = Modifier.height(16.dp))
}
*
* */

@Preview(showBackground = true)
@Composable
fun MainScreenHeaderPreview() {
    MainScreenHeader()
}