package com.example.taskyapplication.agenda.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// delete text button
@Composable
fun AgendaItemDeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp),
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

// delete bottom sheet
@Composable
fun TaskDeleteBottomSheet(
    onDeleteTask: () -> Unit = {},
    onCancelDelete: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(
                topEnd = 30.dp,
                topStart = 30.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(color = Color.Black)
                .height(204.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(color = taskyColors.surface)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Delete task?",
                    style = TaskyTypography.headlineMedium,
                    color = taskyColors.primary
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "This action cannot be reversed",
                    style = TaskyTypography.bodyMedium,
                    color = taskyColors.onSurface
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .height(52.dp)
                            .width(156.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = taskyColors.onSurfaceVariant,
                        ),
                        onClick = onCancelDelete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = taskyColors.surface,
                            contentColor = taskyColors.onSurface,
                        )
                    ) {
                        Text(text = "CANCEL")
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .height(52.dp)
                            .width(156.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = taskyColors.error,
                        ),
                        onClick = onCancelDelete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = taskyColors.error,
                            contentColor = taskyColors.onPrimary,
                        )
                    ) {
                        Text(text = "DELETE")
                    }
                }
            }

        }
    }
}

// start and end time
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgendaItemDateTimeRow(
    modifier: Modifier = Modifier,
    onClickTime: () -> Unit = {},
    onClickDate: () -> Unit = {},
    isEditing: Boolean = false,
    dateText: String = LocalDate.now().format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    ),
    timeText: String = LocalTime.now().format(
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    )
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(2f),
            text = "At",
            style = TaskyTypography.bodyMedium,
            color = taskyColors.primary
        )
        DateTimeFieldWithIcon(
            dateOrTimeText = timeText,
            isEditing = isEditing,
            onClickItem = onClickTime,
        )
        DateTimeFieldWithIcon(
            dateOrTimeText = dateText,
            isEditing = isEditing,
            onClickItem = onClickDate,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimeFieldWithIcon(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit = {},
    dateOrTimeText: String = "",
    isEditing: Boolean = false
) {
    val backgroundColor = colorResource(id = R.color.date_time_background)
    Row(
        modifier = modifier
            .drawBehind {
                drawRoundRect(
                    color = backgroundColor,
                    cornerRadius = CornerRadius(6.dp.toPx()),
                )
            }
            .height(52.dp)
            .clickable { onClickItem() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 12.dp, end = 16.dp),
            text = dateOrTimeText,
            style = TaskyTypography.bodyMedium,
            color = taskyColors.primary
        )
        if (isEditing) {
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp),
                painter = painterResource(R.drawable.dropdown),
                tint = taskyColors.primary,
                contentDescription = ""
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DeleteButtonPreview() {
    AgendaItemDeleteButton(onClick = {})
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AgendaItemTimeRowPreview() {
    AgendaItemDateTimeRow(
        isEditing = true,
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteTaskPreview() {
    TaskDeleteBottomSheet()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EditDateTimePreview() {
    DateTimeFieldWithIcon(
        isEditing = true,
        onClickItem = {},
    )
}
