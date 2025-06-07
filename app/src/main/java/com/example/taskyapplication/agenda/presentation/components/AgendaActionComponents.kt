package com.example.taskyapplication.agenda.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun AgendaItemDateTimeRow(
    modifier: Modifier = Modifier,
    onClickTime: () -> Unit = {},
    onClickDate: () -> Unit = {},
    isEditing: Boolean = false,
    timeRowLabel: String = "At",
    dateText: String,
    timeText: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(end = 12.dp),
            text = timeRowLabel,
            style = TaskyTypography.bodyMedium,
            color = taskyColors.primary
        )
        DateTimeFieldWithIcon(
            modifier = Modifier.weight(1.5f),
            dateOrTimeText = timeText,
            isEditing = isEditing,
            onClickItem = onClickTime,
        )
        DateTimeFieldWithIcon(
            modifier = Modifier.weight(2f),
            dateOrTimeText = dateText,
            isEditing = isEditing,
            onClickItem = onClickDate,
        )
    }
}

@Composable
fun DateTimeFieldWithIcon(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit = {},
    onUpdateDateOrTime: (String) -> Unit = {},
    isEditing: Boolean = false,
    dateOrTimeText: String
) {
    TextField(
        modifier = modifier
            .padding(end = 8.dp),
        value = dateOrTimeText,
        onValueChange = {
            onUpdateDateOrTime(it)
        },
        shape = RoundedCornerShape(4.dp),
        readOnly = true,
        enabled = false,
        singleLine = true,
        textStyle = TaskyTypography.bodyMedium.copy(color = taskyColors.primary),
        colors = TextFieldDefaults.colors(
            disabledTextColor = taskyColors.primary,
            unfocusedContainerColor = taskyColors.surfaceContainerHigh,
            focusedContainerColor = taskyColors.surfaceContainerHigh,
            disabledContainerColor = taskyColors.surfaceContainerHigh,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            if (isEditing) {
                Icon(
                    modifier = Modifier.clickable { onClickItem() },
                    painter = painterResource(R.drawable.dropdown),
                    tint = taskyColors.primary,
                    contentDescription = "Tap to edit date or time"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AgendaItemTimeRowPreview() {
    AgendaItemDateTimeRow(
        timeText = "10:00 AM",
        dateText = "2023-10-01",
        isEditing = true,
    )
}
