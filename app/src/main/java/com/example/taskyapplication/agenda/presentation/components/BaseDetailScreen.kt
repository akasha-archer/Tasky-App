package com.example.taskyapplication.agenda.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.agenda.presentation.AgendaItem
import com.example.taskyapplication.main.components.TaskyBaseScreen
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaseDetailScreen(
    modifier: Modifier = Modifier,
    agendaItemType: String = "",
    agendaItemTitle: String = "",
    agendaItemDescription: String = ""
) {
    var isInEditMode by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
                if (isInEditMode) {
                    EditScreenHeader(
                        itemToEdit = "Reminder",
                        onClickSave = {
                            isInEditMode = !isInEditMode
                        },
                        onClickCancel = {
                            isInEditMode = !isInEditMode
                        }
                    )
                } else {
                    DetailScreenHeader(
                        onClickEdit = { isInEditMode = !isInEditMode }
                    )
                }
            },
            mainContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxHeight()
                            .padding(
                                horizontal = 16.dp
                            )
                            .padding(top = 48.dp)
                    ) {
                        AgendaItem(
                            agendaItemType = {
                                AgendaIconTextRow(
                                    itemIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.agenda_type_square),
                                            tint = taskyColors.primary,
                                            contentDescription = ""
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            modifier = Modifier.padding(start = 8.dp),
                                            text = agendaItemType.uppercase(),
                                            style = TaskyTypography.labelMedium,
                                            color = taskyColors.onSurface
                                        )
                                    }
                                )
                            },
                            agendaItemTitle = {
                                AgendaTitleRow(
                                    agendaItemTitle = agendaItemTitle,
                                    isEditing = isInEditMode
                                )
                            },
                            agendaItemDescription = {
                                AgendaDescriptionText(
                                    agendaItemDescription = agendaItemDescription,
                                    isEditing = isInEditMode
                                )
                            },
                            agendaItemStartTime = {
                                AgendaItemTimeRow(
                                    isEditing = isInEditMode,
                                    onClickDate = {},
                                    onClickTime = {},
                                )
                            },
                            agendaItemReminderTime = {
                                ReminderTimeRow(
                                    isEditing = isInEditMode,
                                )
                            },
                        )
                        AgendaItemDeleteButton(
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .align(Alignment.BottomCenter),
                            onClick = {}
                        )
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BaseDetailScreenPreview() {
    BaseDetailScreen(
        agendaItemType = "Task",
        agendaItemTitle = "Sample Task",
        agendaItemDescription = "This is a sample task description" +
                " \n Second line of description" +
                "\n Third line of description"
    )
}