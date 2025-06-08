package com.example.taskyapplication.agenda.items.event.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.AgendaItemAction
import com.example.taskyapplication.agenda.items.event.components.PhotoRow
import com.example.taskyapplication.agenda.items.event.components.PhotoRowEmptyState
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState
import com.example.taskyapplication.agenda.presentation.components.AgendaDescriptionText
import com.example.taskyapplication.agenda.presentation.components.AgendaIconTextRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItem
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDateTimeRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDeleteTextButton
import com.example.taskyapplication.agenda.presentation.components.AgendaTitleRow
import com.example.taskyapplication.agenda.presentation.components.DetailScreenHeader
import com.example.taskyapplication.agenda.presentation.components.EditScreenHeader
import com.example.taskyapplication.agenda.presentation.components.ReminderTimeRow
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun EventDateTimeScreen(
    modifier: Modifier = Modifier,
    agendaItem: String = "Event",
    onAction: (AgendaItemAction) -> Unit = {},
    isEditScreen: Boolean = true,
    state: EventUiState
) {
    var selectedImageUris by remember { mutableStateOf(state.photos) }
    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            selectedImageUris = uris
        }
    )

    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
                EditScreenHeader(
                    itemToEdit = "Event",
                    onClickSave = {
                        onAction(AgendaItemAction.SaveDateTimeEdit)
                        onAction(AgendaItemAction.SaveSelectedPhotos(selectedImageUris))
                    },
                    onClickCancel = {
                        onAction(AgendaItemAction.CancelEdit)
                    }
                )
            },
            mainContent = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .padding(top = 48.dp)
                    ) {
                        AgendaItem(
                            modifier = modifier.fillMaxWidth(),
                            agendaItemType = {
                                AgendaIconTextRow(
                                    itemIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.CheckCircle, // Or another square icon
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(end = 12.dp)
                                                .height(24.dp)
                                                .width(24.dp),
                                            tint = Color.Blue
                                        )
                                    },
                                    textItem = {
                                        Text(
                                            text = agendaItem.uppercase(),
                                            style = TaskyTypography.labelMedium,
                                            color = taskyColors.onSurface
                                        )
                                    },
                                )
                            },
                            agendaItemTitle = {
                                AgendaTitleRow(
                                    agendaItemTitle = state.title,
                                    isEditing = isEditScreen,
                                )
                            },
                            agendaItemDescription = {
                                AgendaDescriptionText(
                                    agendaItemDescription = state.description,
                                    isEditing = isEditScreen,
                                )
                            },
                            eventMedia = {
                                if (state.photos.isEmpty()) {
                                    PhotoRowEmptyState(
                                        launchPhotoPicker = {
                                            photoLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        },
                                    )
                                } else {
                                    PhotoRow(
                                        modifier = Modifier,
                                        photos = selectedImageUris
                                    )
                                }
                            },
                            agendaItemStartTime = {  // event starting time
                                AgendaItemDateTimeRow(
                                    isEditing = isEditScreen,
                                    timeRowLabel = "From",
                                    timeText = state.startTime,
                                    dateText = state.startDate,
                                )
                            },
                            agendaItemEndTime = {  // event end time
                                AgendaItemDateTimeRow(
                                    isEditing = isEditScreen,
                                    timeRowLabel = "To",
                                    timeText = state.endTime,
                                    dateText = state.endDate,
                                )
                            },
                            agendaItemReminderTime = {
                                ReminderTimeRow(
                                    reminderTime = state.remindAt.timeString,
                                    isEditing = isEditScreen,
                                )
                            }
                        )
                        AgendaItemDeleteTextButton(
                            modifier = Modifier
                                .padding(bottom = 36.dp)
                                .align(Alignment.BottomEnd),
                            onClick = { },
                            itemToDelete = agendaItem.uppercase(),
                            isEnabled = state.id.isNotEmpty()
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventDateTimePreview() {
    EventDateTimeScreen(
        state = EventUiState()
    )
}
