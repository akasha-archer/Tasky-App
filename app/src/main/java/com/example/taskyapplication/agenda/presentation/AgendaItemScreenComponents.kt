package com.example.taskyapplication.agenda.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
fun AgendaDescriptionText(
    modifier: Modifier = Modifier,
    description: TextFieldState? = rememberTextFieldState()
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        text = description?.text.toString(),
        textAlign = TextAlign.Left,
        overflow = TextOverflow.Ellipsis,
        style = TaskyTypography.bodyMedium,
        color = taskyColors.primary
    )
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgendaItemTimeRow(
    modifier: Modifier = Modifier,
    onClickTime: () -> Unit = {},
    onClickDate: () -> Unit = {}
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
                .weight(1f),
            text = "At",
            style = TaskyTypography.bodyMedium,
            color = taskyColors.primary
        )
        Text(
            modifier = Modifier
                .clickable { onClickTime() }
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xFFF2F3F7),
                        cornerRadius = CornerRadius(3.dp.toPx())
                    )
                }.padding(vertical = 10.dp).padding(start = 24.dp, end = 32.dp),
            text = LocalTime.now().format(
                DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            ),
            style = TaskyTypography.bodyMedium,
            color = taskyColors.primary
        )
        Text(
            modifier = Modifier
                .clickable { onClickDate() }
                .drawBehind {
                    drawRoundRect(
                        Color(0xFFF2F3F7),
                        cornerRadius = CornerRadius(3.dp.toPx()),
                    )
                }.padding(vertical = 10.dp).padding(start = 24.dp, end = 32.dp),
            text = LocalDate.now().format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            ),
            style = TaskyTypography.bodyMedium,
            color = taskyColors.primary
        )
    }
}

@Composable
fun AgendaIconTextRow(
    modifier: Modifier = Modifier,
    itemIcon: @Composable () -> Unit,
    textItem: @Composable () -> Unit
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

@Preview(showBackground = true)
@Composable
fun AgendaTypePreview() {
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
        }
    )
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
    AgendaItemTimeRow()
}
