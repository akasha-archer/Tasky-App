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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.taskyapplication.agenda.domain.toLocalDateAndTime
import com.example.taskyapplication.agenda.domain.toTimeAsString
import com.example.taskyapplication.agenda.items.event.EventItemAction
import com.example.taskyapplication.agenda.items.event.SharedEventViewModel
import com.example.taskyapplication.agenda.items.event.components.PhotoRow
import com.example.taskyapplication.agenda.items.event.components.PhotoRowEmptyState
import com.example.taskyapplication.agenda.items.event.components.VisitorHeader
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
import java.time.LocalDate
import java.time.LocalTime

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
    val namelist by eventViewModel.tempAttendeeList.collectAsStateWithLifecycle()
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
                tempVisitorList = namelist,
                onAction = { action ->
                    when (action) {
                        EventItemAction.SaveDateTimeEdit -> onClickSave()
                        EventItemAction.CancelEdit -> onClickCancel()
                        EventItemAction.LaunchEditTitleScreen -> onSelectEditTitle()
                        EventItemAction.LaunchEditDescriptionScreen -> onSelectEditDescription()
                        else -> {
                            Unit
                        }
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
    tempVisitorList: List<String> = emptyList(),
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
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10),
        onResult = { uris ->
            selectedImageUris = selectedImageUris + uris // Append new URIs
            // Ensure you don't exceed 10 total photos
            val totalPhotos = persistedImageUrls.size + selectedImageUris.size
            if (totalPhotos > 10) {
                selectedImageUris = selectedImageUris.take(10 - persistedImageUrls.size)
            }
             onAction(EventItemAction.SaveSelectedPhotos(selectedImageUris))
        }
    )
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(
        is24Hour = false
    )
    val ctxt = LocalContext.current

    var showStartTimePicker by rememberSaveable { mutableStateOf(false) }
    var showStartDatePicker by rememberSaveable { mutableStateOf(false) }
    var showEndTimePicker by rememberSaveable { mutableStateOf(false) }
    var showEndDatePicker by rememberSaveable { mutableStateOf(false) }

    var showVisitorBottomSheet by rememberSaveable { mutableStateOf(false) }
    var userEmail by rememberSaveable { mutableStateOf("") }

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
                                if (combinedImageList.isEmpty()) { // Show empty state if no photos at all
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
                                        photosToShow = combinedImageList,
                                        showFeatureDisabledMessage = {
                                            Toast.makeText(
                                                ctxt,
                                                "Feature not available when device is offline",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        onAddPhotoClick = {
                                            if (combinedImageList.size < 10) { // Check before launching
                                                photoLauncher.launch(
                                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                )
                                            }
                                        }
                                    )
                                }
                            },
                            agendaItemStartTime = {  // event starting time
                                AgendaItemDateTimeRow(
                                    isEditing = isEditScreen,
                                    timeRowLabel = "From",
                                    timeText = state.startTime.toTimeAsString(),
                                    dateText = state.startDate.toDateAsString(),
                                    onClickTime = {
                                        onAction(EventItemAction.ShowTimePicker)
                                        showStartTimePicker = true
                                    },
                                    onClickDate = {
                                        onAction(EventItemAction.ShowDatePicker)
                                        showStartDatePicker = true
                                    },
                                )
                            },
                            agendaItemEndTime = {  // event end time
                                AgendaItemDateTimeRow(
                                    isEditing = isEditScreen,
                                    timeRowLabel = "To",
                                    timeText = state.endTime.toTimeAsString(),
                                    dateText = state.endDate.toDateAsString(),
                                    onClickTime = {
                                        onAction(EventItemAction.ShowTimePicker)
                                        showEndTimePicker = true
                                    },
                                    onClickDate = {
                                        onAction(EventItemAction.ShowDatePicker)
                                        showEndDatePicker = true
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
                            eventVisitorSection = {
                                VisitorHeader(
                                    modifier = Modifier,
                                    isEditingScreen = true,
                                    onAddNewVisitor = {
                                        onAction(EventItemAction.AddNewVisitor(userEmail))
                                    },
                                    onCancelAddingVisitor = {
                                        showVisitorBottomSheet = false
                                    },
                                    showAddVisitorBottomSheet = {
                                        showVisitorBottomSheet = true
                                    },
                                    showFeatureDisabledMessage = {
                                        Toast.makeText(
                                            ctxt,
                                            "Feature not available when device is offline",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    isBottomSheetEnabled = showVisitorBottomSheet,
                                    isLoading = state.isValidatingAttendee,
                                    isValidEmail = state.isValidUser,
                                    userEmail = userEmail,
                                    visitorList = tempVisitorList
                                )
                            },
                            launchDatePicker = { // start date pickers
                                if (showStartDatePicker) {
                                    TaskyDatePicker(
                                        datePickerState = datePickerState,
                                        onDismiss = {
                                            showStartDatePicker = false
                                            onAction(EventItemAction.HideDatePicker)
                                        },
                                        onConfirm = {
                                            onAction(
                                                EventItemAction.SetStartDate(
                                                    datePickerState.selectedDateMillis?.toLocalDateAndTime()?.first ?: LocalDate.now()
                                                )
                                            )
                                            showStartDatePicker = false
                                        },
                                    )
                                } // start date picker

                                if (showEndDatePicker) {
                                    TaskyDatePicker(
                                        datePickerState = datePickerState,
                                        onDismiss = {
                                            onAction(EventItemAction.HideDatePicker)
                                            showEndDatePicker = false
                                        },
                                        onConfirm = {
                                            onAction(
                                                EventItemAction.SetEndDate(
                                                    datePickerState.selectedDateMillis?.toLocalDateAndTime()?.first ?: LocalDate.now()
                                                )
                                            )
                                            showEndDatePicker = false
                                        },
                                    )
                                } // end date picker

                            },
                            launchTimePicker = { // time pickers
                                if (showStartTimePicker) {
                                    TaskyTimePicker(
                                        timePickerState = timePickerState,
                                        onDismiss = {
                                            showStartTimePicker = false
                                            onAction(EventItemAction.HideTimePicker)
                                        },
                                        onConfirm = {
                                            onAction(
                                                EventItemAction.SetStartTime(
                                                    LocalTime.of(timePickerState.hour, timePickerState.minute)
                                                )
                                            )
                                            showStartTimePicker = false
                                        },
                                    )
                                }// start time picker

                                if (showEndTimePicker) {
                                    TaskyTimePicker(
                                        timePickerState = timePickerState,
                                        onDismiss = {
                                            showEndTimePicker = false
                                            onAction(EventItemAction.HideTimePicker)
                                        },
                                        onConfirm = {
                                            onAction(
                                                EventItemAction.SetEndTime(
                                                    LocalTime.of(timePickerState.hour, timePickerState.minute)
                                                )
                                            )
                                            showEndTimePicker = false
                                        },
                                    )
                                }// end time picker
                            },
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
