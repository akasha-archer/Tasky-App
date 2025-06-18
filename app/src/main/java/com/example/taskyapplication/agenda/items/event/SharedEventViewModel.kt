package com.example.taskyapplication.agenda.items.event

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.common.AgendaItemEvent
import com.example.taskyapplication.agenda.items.event.data.toCreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.toEventUiState
import com.example.taskyapplication.agenda.items.event.data.toUpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.domain.EventRepository
import com.example.taskyapplication.agenda.items.event.domain.ImageMultiPartProvider
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SharedEventViewModel @Inject constructor(
//    private val imageMultiPartProvider: ImageMultiPartProvider,
    private val eventRepository: EventRepository,
//    @ApplicationContext private val applicationContext: Context,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val eventId: String? = savedStateHandle.get<String>("eventId")

    init {
        if (eventId != null) {
            loadExistingEvent(eventId)
        }
    }

    private val agendaEventChannel = Channel<AgendaItemEvent>()
    val agendaEvents = agendaEventChannel.receiveAsFlow()

    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState = _eventUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = EventUiState()
    )

    private val _uploadedPhotos = MutableStateFlow<List<Uri>>(emptyList())
    val uploadedPhotos = _uploadedPhotos.asStateFlow()

    private val _tempAttendeeList = MutableStateFlow<List<String>>(emptyList())
    val tempAttendeeList = _tempAttendeeList.asStateFlow()

    private fun loadExistingEvent(eventId: String) {
        viewModelScope.launch {
            val requestedTask = eventRepository.getEventWithoutImages(eventId)
            _eventUiState.value = requestedTask.toEventUiState()
        }
    }
    private fun isNewEvent(currentId: String) = currentId.isEmpty() || currentId.isBlank()

    private fun createOrUpdateEvent() {
        viewModelScope.launch {
            _eventUiState.update { it.copy(isEditingItem = true) }
            val newTitle = _eventUiState.value.title
            val newDescription = _eventUiState.value.description
            val eventAttendees = _eventUiState.value.attendeeIds
            val eventStartTime = _eventUiState.value.startTime
            val eventEndTime = _eventUiState.value.endTime
            val eventStartDate = _eventUiState.value.startDate
            val eventEndDate = _eventUiState.value.endDate
            val eventNotificationTime = _eventUiState.value.remindAt
            val currentId = _eventUiState.value.id

            var eventId = ""
            if (isNewEvent(currentId)) {
                _eventUiState.update { it.copy(id = UUID.randomUUID().toString()) }
                eventId = _eventUiState.value.id
            }

            val photosToUpload = _uploadedPhotos.value

            val eventToCreateOrUpdate = EventUiState(
                id = if (isNewEvent(currentId)) eventId else currentId,
                title = newTitle,
                description = newDescription,
                attendeeIds = eventAttendees,
                startTime = eventStartTime,
                endTime = eventEndTime,
                startDate = eventStartDate,
                endDate = eventEndDate,
                remindAt = eventNotificationTime
            )
            val result = if (isNewEvent(currentId)) {
                eventRepository.createNewEvent(
                    createEventNetworkModel = eventToCreateOrUpdate.toCreateEventNetworkModel(),
                    photos = eventRepository.createMultiPartImages( photosToUpload)
                )
            } else {
                eventRepository.updateEvent(
                    updateEventNetworkModel = eventToCreateOrUpdate.toUpdateEventNetworkModel(),
                    photos = eventRepository.createMultiPartImages( photosToUpload)
                )
            }
            _eventUiState.update { it.copy(isEditingItem = false) }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.NO_INTERNET) {
                        agendaEventChannel.send(
                            AgendaItemEvent.NewItemCreatedError(
                                errorMessage = "Item not updated. Please check your internet connection."
                            )
                        )
                    } else {
                        agendaEventChannel.send(
                            AgendaItemEvent.NewItemCreatedError(
                                "Something went wrong. Please try again later."
                            )
                        )
                    }
                }

                is Result.Success -> {
                    agendaEventChannel.send(AgendaItemEvent.NewItemCreatedSuccess)
                }
            }
        }
    }

    private fun verifyEventAttendee(email: String) {
        viewModelScope.launch {
            _eventUiState.update { it.copy(isValidatingAttendee = true) }
            val result = eventRepository.validateAttendee(email)
            _eventUiState.update { it.copy(isValidatingAttendee = false) }

            when (result) {
                is Result.Error -> {}
                is Result.Success -> {
                    if (result.data.doesUserExist) {
                        _eventUiState.update { it.copy(
                            isValidUser = true
                        )
                        }
                        _tempAttendeeList.update { it + result.data.attendee.fullName }
                    }
                }
            }
        }
    }

    private fun deleteEventById(eventId: String) {
        viewModelScope.launch {
            _eventUiState.update { it.copy(isDeletingItem = true) }
            val result = eventRepository.deleteEvent(eventId)
            _eventUiState.update { it.copy(isDeletingItem = false) }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.NO_INTERNET) {
                        agendaEventChannel.send(
                            AgendaItemEvent.DeleteError(
                                errorMessage = "Item not deleted. Please check your internet connection."
                            )
                        )
                    } else {
                        agendaEventChannel.send(
                            AgendaItemEvent.DeleteError(
                                "Something went wrong. Please try again later."
                            )
                        )
                    }
                }

                is Result.Success -> {
                    agendaEventChannel.send(AgendaItemEvent.DeleteSuccess)
                }
            }
        }
    }

    fun executeActions(action: EventItemAction) {
        when (action) {
            EventItemAction.SaveAgendaItemUpdates -> { createOrUpdateEvent() }

            is EventItemAction.EditExistingEvent -> {
                loadExistingEvent(action.eventId)
            }
            is EventItemAction.OpenExistingEvent -> {
                loadExistingEvent(action.eventId)
            }
            is EventItemAction.SetTitle -> {
                viewModelScope.launch {
                    _eventUiState.update { it.copy(title = action.title) }
                }
            }

            is EventItemAction.SetDescription -> {
                viewModelScope.launch {
                    _eventUiState.update { it.copy(description = action.description) }
                }
            }

            is EventItemAction.SetReminderTime -> {
                viewModelScope.launch {
                    _eventUiState.update { it.copy(remindAt = action.reminder) }
                }
            }

            is EventItemAction.SaveSelectedPhotos -> {
                viewModelScope.launch {
                    _uploadedPhotos.update { action.eventPhotos }
                }
            }

            is EventItemAction.SetEndDate -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(
                            endDate = action.endDate,
                            isEditingTime = false
                        )
                    }
                }
            }
            is EventItemAction.SetEndTime -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(
                            endTime = action.endTime,
                            isEditingTime = false
                        )
                    }
                }
            }
            is EventItemAction.SetStartDate -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(
                            startDate = action.startDate,
                            isEditingTime = false
                        )
                    }
                }
            }
            is EventItemAction.SetStartTime -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(
                            startTime = action.startTime,
                            isEditingTime = false
                        )
                    }
                }
            }
            is EventItemAction.DeleteEvent -> {
                viewModelScope.launch {
                    deleteEventById(action.eventId)
                }
            }
            is EventItemAction.AddNewVisitor -> {
                viewModelScope.launch {
                    verifyEventAttendee(action.visitorEmail)
                }
            }

            EventItemAction.ShowDatePicker -> {
                viewModelScope.launch {
                    _eventUiState.update { it.copy(isEditingDate = true) }
                }
            }

            EventItemAction.HideDatePicker -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(isEditingDate = false)
                    }
                }
            }

            EventItemAction.ShowReminderDropDown -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(isEditingReminder = true)
                    }
                }
            }

            EventItemAction.HideReminderDropDown -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(isEditingReminder = false)
                    }
                }
            }

            EventItemAction.ShowTimePicker -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(isEditingTime = true)
                    }
                }
            }

            EventItemAction.HideTimePicker -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(isEditingTime = false)
                    }
                }
            }

            EventItemAction.CloseEditDescriptionScreen -> {
                viewModelScope.launch {
                    _eventUiState.update {
                        it.copy(isEditingItem = false)
                    }
                }
            }

            EventItemAction.CloseEditTitleScreen -> {
                _eventUiState.update {
                    it.copy(isEditingItem = false)
                }
            }

            EventItemAction.LaunchEditDescriptionScreen -> {
                _eventUiState.update {
                    it.copy(isEditingItem = true)
                }
            }

            EventItemAction.LaunchEditTitleScreen -> {
                _eventUiState.update {
                    it.copy(isEditingItem = true)
                }
            }

            EventItemAction.CancelEdit -> {
                _eventUiState.update {
                    it.copy(
                        isEditingItem = false,
                        isEditingDate = false,
                        isEditingTime = false,
                        isEditingReminder = false
                    )
                }
            }

            EventItemAction.LaunchDateTimeEditScreen -> {
                _eventUiState.update {
                    it.copy(isEditingItem = true)
                }
            }
            // go back to Agenda screen
            EventItemAction.CloseDetailScreen -> { Unit }

            EventItemAction.SaveDateTimeEdit -> {
                _eventUiState.update {
                    it.copy(isEditingItem = false)
                }
            }
        }
    }
}
