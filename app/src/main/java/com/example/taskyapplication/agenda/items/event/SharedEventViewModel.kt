package com.example.taskyapplication.agenda.items.event

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.common.AgendaItemEvent
import com.example.taskyapplication.agenda.common.INetworkObserver
import com.example.taskyapplication.agenda.common.NetworkStatus
import com.example.taskyapplication.agenda.items.event.data.toCreateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.data.toEventUiState
import com.example.taskyapplication.agenda.items.event.data.toUpdateEventNetworkModel
import com.example.taskyapplication.agenda.items.event.domain.EventRepository
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SharedEventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val networkObserver: INetworkObserver,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val eventId: String? = savedStateHandle.get<String>("eventId")

    private val agendaEventChannel = Channel<AgendaItemEvent>()
    val agendaEvents = agendaEventChannel.receiveAsFlow()

    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState = _eventUiState
        .onStart {
            networkObserver.networkStatus.collect { status ->
                val isOnline = status == NetworkStatus.Available
                _eventUiState.update { it.copy(isUserOnline = isOnline) }
            }
            if (eventId != null) {
                loadExistingEvent(eventId)
            }
        }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = EventUiState()
    )

    private val _uploadedPhotos = MutableStateFlow<List<Uri>>(emptyList())
    val uploadedPhotos = _uploadedPhotos.asStateFlow()

    private fun loadExistingEvent(eventId: String) {
        viewModelScope.launch {
            val requestedTask = eventRepository.getEventWithoutImages(eventId)
            if (requestedTask != null) {
                _eventUiState.value = requestedTask.toEventUiState()
            }
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
                    request = eventToCreateOrUpdate.toCreateEventNetworkModel(),
                    photos = eventRepository.createMultiPartImages( photosToUpload)
                )
            } else {
                eventRepository.updateEvent(
                    request = eventToCreateOrUpdate.toUpdateEventNetworkModel(),
                    photos = eventRepository.createMultiPartImages( photosToUpload)
                )
            }
            _eventUiState.update { it.copy(isEditingItem = false) }

            when {
                result.isSuccess -> {
                    agendaEventChannel.send(AgendaItemEvent.NewItemCreatedSuccess)
                }

                result.isFailure -> {
                    agendaEventChannel.send(
                        AgendaItemEvent.NewItemCreatedError(
                            "Something went wrong. Please try again later."
                        )
                    )
                }
            }
        }
    }

    private fun verifyEventAttendee(email: String) {
        viewModelScope.launch {
            _eventUiState.update { it.copy(isValidatingAttendee = true) }
            val validationResult = eventRepository.validateAttendee(email)

            validationResult.fold(
                onSuccess = { attendeeResponse ->
                    _eventUiState.update { currentState ->
                        val updatedAttendeeList = currentState.attendeeNames + attendeeResponse.attendee.fullName
                        currentState.copy(
                            isValidatingAttendee = false,
                            isValidUser = true,
                            attendeeNames = updatedAttendeeList.distinct(),
                        )
                    }
                },
                onFailure = { exception ->
                    _eventUiState.update {
                        it.copy(
                            isValidatingAttendee = false,
                            isValidUser = false
                        )
                    }
                     agendaEventChannel.send(AgendaItemEvent.AttendeeValidationError(exception.message))
                }
            )
        }
    }

    private fun deleteEventById(eventId: String) {
        viewModelScope.launch {
            _eventUiState.update { it.copy(isDeletingItem = true) }
            val result = eventRepository.deleteEvent(eventId)
            _eventUiState.update { it.copy(isDeletingItem = false) }

            when {
                result.isSuccess -> {
                    agendaEventChannel.send(AgendaItemEvent.NewItemCreatedSuccess)
                }

                result.isFailure -> {
                    agendaEventChannel.send(
                        AgendaItemEvent.NewItemCreatedError(
                            "Event could not be deleted. Please try again later."
                        )
                    )
                }
            }
        }
    }

    fun executeActions(action: EventItemAction) {
        when (action) {
            EventItemAction.SaveAgendaItemUpdates -> {
                createOrUpdateEvent()
            }

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
            EventItemAction.CloseDetailScreen -> {
                Unit
            }

            EventItemAction.SaveDateTimeEdit -> {
                _eventUiState.update {
                    it.copy(isEditingItem = false)
                }
            }
        }
    }
}
