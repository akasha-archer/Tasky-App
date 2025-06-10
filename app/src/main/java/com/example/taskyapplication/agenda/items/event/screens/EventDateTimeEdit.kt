package com.example.taskyapplication.agenda.items.event.screens

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.agenda.common.AgendaItemEvent
import com.example.taskyapplication.agenda.domain.toDateAsString
import com.example.taskyapplication.agenda.items.event.EventItemAction
import com.example.taskyapplication.agenda.items.event.SharedEventViewModel
import com.example.taskyapplication.agenda.items.event.components.PhotoRow
import com.example.taskyapplication.agenda.items.event.components.PhotoRowEmptyState
import com.example.taskyapplication.agenda.items.event.domain.EventImageItem
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState
import com.example.taskyapplication.agenda.presentation.components.AgendaDescriptionText
import com.example.taskyapplication.agenda.presentation.components.AgendaIconTextRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItem
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDateTimeRow
import com.example.taskyapplication.agenda.presentation.components.AgendaItemDeleteTextButton
import com.example.taskyapplication.agenda.presentation.components.AgendaTitleRow
import com.example.taskyapplication.agenda.presentation.components.DeleteItemBottomSheet
import com.example.taskyapplication.agenda.presentation.components.EditScreenHeader
import com.example.taskyapplication.agenda.presentation.components.ReminderTimeRow
import com.example.taskyapplication.agenda.presentation.components.TaskyDatePicker
import com.example.taskyapplication.agenda.presentation.components.TaskyTimePicker
import com.example.taskyapplication.domain.utils.ObserveAsEvents
import com.example.taskyapplication.main.presentation.components.TaskyScaffold
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun EventEditDateTimeRoot(
    modifier: Modifier = Modifier,
    onClickSave: () -> Unit,
    onClickCancel: () -> Unit,
    onSelectEditTitle: () -> Unit,
    onSelectEditDescription: () -> Unit,
    eventViewModel: SharedEventViewModel
) {
    val uiState by eventViewModel.eventUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(eventViewModel.agendaEvents) { event ->
        when (event) {
            is AgendaItemEvent.DeleteError -> {
                Toast.makeText(
                    context,
                    event.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
            AgendaItemEvent.DeleteSuccess -> {
                Toast.makeText(
                    context,
                    "Event has been deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is AgendaItemEvent.NewItemCreatedError -> {
                Toast.makeText(
                    context,
                    event.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
            AgendaItemEvent.NewItemCreatedSuccess -> {
                Toast.makeText(
                    context,
                    "Your new Event has been created",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is AgendaItemEvent.UpdateItemError -> {
                Toast.makeText(
                    context,
                    event.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
            AgendaItemEvent.UpdateItemSuccess -> {
                Toast.makeText(
                    context,
                    "Your Event has been updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    TaskyScaffold(
        modifier = modifier,
        mainContent = {
            EventDateTimeScreen(
                modifier = modifier,
                state = uiState,
                onAction = { action ->
                    when(action) {
                        EventItemAction.SaveDateTimeEdit -> onClickSave()
                        EventItemAction.CancelEdit -> onClickCancel()
                        EventItemAction.LaunchEditTitleScreen -> onSelectEditTitle()
                        EventItemAction.LaunchEditDescriptionScreen -> onSelectEditDescription()
                        else -> { Unit }
                    }
                    eventViewModel.executeActions(action)
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDateTimeScreen(
    modifier: Modifier = Modifier,
    agendaItem: String = "Event",
    onAction: (EventItemAction) -> Unit = {},
    isEditScreen: Boolean = true,
    state: EventUiState
) {
    // persisted photos are stored and displayed as urls
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val persistedImageUrls: List<String> = state.photos

    // This list gets populated when the user picks new photos
    val newImageUris: List<Uri> = selectedImageUris

    val urlItems = persistedImageUrls.map { EventImageItem.PersistedImage(it) }
    val uriItems = newImageUris.map { EventImageItem.NewImage(it) }
    val combinedImageList = urlItems + uriItems

    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            selectedImageUris = uris
        }
    )
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(
        is24Hour = false
    )
    val timeOfDay = if (timePickerState.isAfternoon) "PM" else "AM"

    Column(
        modifier = modifier
            .padding(top = 48.dp)
    ) {
        TaskyBaseScreen(
            screenHeader = {
                EditScreenHeader(
                    itemToEdit = "Event",
                    onClickSave = {
                        onAction(EventItemAction.SaveDateTimeEdit)
                        onAction(EventItemAction.SaveSelectedPhotos(selectedImageUris))
                        onAction(EventItemAction.SaveAgendaItemUpdates)
                    },
                    onClickCancel = {
                        onAction(EventItemAction.CancelEdit)
                    }
                )
            },
            mainContent = {
                var showDeleteBottomSheet by remember { mutableStateOf(false) }
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
                                    onClickEdit = {
                                        onAction(EventItemAction.LaunchEditTitleScreen)
                                    }
                                )
                            },
                            agendaItemDescription = {
                                AgendaDescriptionText(
                                    agendaItemDescription = state.description,
                                    isEditing = isEditScreen,
                                    onClickEdit = {
                                        onAction(EventItemAction.LaunchEditDescriptionScreen)
                                    }
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
                                        photosToShow = combinedImageList
                                    )
                                }
                            },
                            agendaItemStartTime = {  // event starting time
                                AgendaItemDateTimeRow(
                                    isEditing = isEditScreen,
                                    timeRowLabel = "From",
                                    timeText = state.startTime,
                                    dateText = state.startDate,
                                    onClickTime = {
                                        onAction(EventItemAction.ShowTimePicker)
                                    },
                                    onClickDate = {
                                        onAction(EventItemAction.ShowDatePicker)
                                    },
                                )
                            },
                            agendaItemEndTime = {  // event end time
                                AgendaItemDateTimeRow(
                                    isEditing = isEditScreen,
                                    timeRowLabel = "To",
                                    timeText = state.endTime,
                                    dateText = state.endDate,
                                    onClickTime = {
                                        onAction(EventItemAction.ShowTimePicker)
                                    },
                                    onClickDate = {
                                        onAction(EventItemAction.ShowDatePicker)
                                    },
                                )
                            },
                            agendaItemReminderTime = {
                                ReminderTimeRow(
                                    reminderTime = state.remindAt.timeString,
                                    isEditing = isEditScreen,
                                    onClickDropDown = {
                                        onAction(EventItemAction.ShowReminderDropDown)
                                    }
                                )
                            },
                            launchDatePicker = { // date - time pickers
                                if (state.isEditingDate) {
                                    TaskyDatePicker(
                                        datePickerState = datePickerState,
                                        onDismiss = {
                                            onAction(EventItemAction.HideDatePicker)
                                        },
                                        onConfirm = {
                                            onAction(
                                                EventItemAction.SetStartDate(
                                                    datePickerState.selectedDateMillis?.toDateAsString()
                                                        ?: state.startDate
                                                )
                                            )
                                        },
                                    )
                                }
                            },
                            launchTimePicker = {
                                if (state.isEditingTime) {
                                    TaskyTimePicker(
                                        timePickerState = timePickerState,
                                        onDismiss = {
                                            onAction(EventItemAction.HideTimePicker)
                                        },
                                        onConfirm = {
                                            onAction(
                                                EventItemAction.SetStartTime(
                                                    timePickerState.hour.toString() + ":" + timePickerState.minute.toString() + " $timeOfDay"
                                                )
                                            )
                                        },
                                    )
                                }
                            }, // end date - time pickers
                        )
                        AgendaItemDeleteTextButton(
                            modifier = Modifier
                                .padding(bottom = 36.dp)
                                .align(Alignment.BottomEnd),
                            onClick = {
                                showDeleteBottomSheet = true
                            },
                            itemToDelete = "Event".uppercase(),
                            isEnabled = state.id.isNotEmpty()
                        )
                        if (showDeleteBottomSheet) {
                            DeleteItemBottomSheet(
                                modifier = Modifier,
                                isLoading = state.isDeletingItem,
                                isButtonEnabled = !state.isDeletingItem,
                                onDeleteTask = {
                                    onAction(EventItemAction.DeleteEvent(state.id))
                                    showDeleteBottomSheet = false
                                },
                                onCancelDelete = {
                                    showDeleteBottomSheet = false
                                }
                            )
                        }
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
        state = EventUiState(),
    )
}
