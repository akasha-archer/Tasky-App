package com.example.taskyapplication.agenda.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// template
@Composable
fun AgendaItemScreenHeader(
    modifier: Modifier = Modifier,
    firstItem: @Composable () -> Unit,
    secondItem: @Composable () -> Unit,
    thirdItem: @Composable () -> Unit
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

@Composable
fun EditInputHeader(
    modifier: Modifier = Modifier,
    itemToEdit: String,
    onClickSave: (String) -> Unit,
    onClickCancel: () -> Unit
) {
    AgendaItemScreenHeader(
        modifier = modifier
            .background(taskyColors.surface),
        firstItem = {
            Text(
                modifier = Modifier
                    .clickable { onClickCancel() },
                text = "Cancel",
                color = taskyColors.onSurface,
                style = TaskyTypography.labelSmall
            )
        },
        secondItem = {
            Text(
                text ="Edit $itemToEdit".uppercase(),
                color = taskyColors.onSurface,
                style = TaskyTypography.labelMedium
            )
        },
        thirdItem = {
            Text(
                modifier = Modifier
                    .clickable { onClickSave },
                text = "Save",
                color = taskyColors.validInput,
                style = TaskyTypography.labelSmall
            )
        }
    )
}

// detail screen header
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreenHeader(
    modifier: Modifier = Modifier,
    onClickEdit: () -> Unit
) {
    AgendaItemScreenHeader(
        modifier = modifier,
        firstItem = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close detail screen",
                tint = taskyColors.onPrimary
            )
        },
        secondItem = {
            Text(
                text = LocalDate.now()
                    .format(
                        DateTimeFormatter.ofPattern(
                            SimpleDateFormat(
                                "dd MMMM yyyy",
                                Locale.getDefault()
                            ).toPattern()
                        )
                    ).uppercase(),
                color = taskyColors.onPrimary,
                style = TaskyTypography.labelMedium
            )
        },
        thirdItem = {
            Icon(
                modifier = Modifier
                    .clickable { onClickEdit() },
                imageVector = Icons.Outlined.Edit,
                contentDescription = "Edit reminder",
                tint = taskyColors.onPrimary
            )
        }
    )
}

// edit screen header
@Composable
fun EditScreenHeader(
    modifier: Modifier = Modifier,
    itemToEdit: String,
    onClickSave: () -> Unit,
    onClickCancel: () -> Unit
) {
    AgendaItemScreenHeader(
        modifier = modifier,
        firstItem = {
            Text(
                modifier = Modifier
                    .clickable { onClickCancel() },
                text = "Cancel",
                color = taskyColors.onPrimary,
                style = TaskyTypography.labelSmall
            )
        },
        secondItem = {
            Text(
                text ="Edit $itemToEdit".uppercase(),
                color = taskyColors.onPrimary,
                style = TaskyTypography.labelMedium
            )
        },
        thirdItem = {
            Text(
                modifier = Modifier
                    .clickable { onClickSave() },
                text = "Save",
                color = taskyColors.validInput,
                style = TaskyTypography.labelSmall
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AgendaItemScreenHeaderPreview() {
    DetailScreenHeader(
        onClickEdit = {}
    )
}

@Preview
@Composable
fun EditScreenHeaderPreview() {
    EditScreenHeader(
        itemToEdit = "Reminder",
        onClickSave = {},
        onClickCancel = {}
    )
}

@Preview
@Composable
fun EditInputHeaderPreview() {
    EditInputHeader(
        itemToEdit = "Reminder",
        onClickSave = {},
        onClickCancel = {}
    )
}