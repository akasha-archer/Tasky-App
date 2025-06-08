package com.example.taskyapplication.agenda.items.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.agenda.items.event.domain.EventRepository
import com.example.taskyapplication.agenda.items.event.domain.ImageMultiPartProvider
import com.example.taskyapplication.agenda.items.event.presentation.EventUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class SharedEventViewModel @Inject constructor(
    private val imageMultiPartProvider: ImageMultiPartProvider,
    private val eventRepository: EventRepository
): ViewModel() {

    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState = _eventUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = EventUiState()
    )

    fun executeActions(action: EventItemAction) {
        when (action) {
            EventItemAction.SaveAgendaItemUpdates -> {
                val newTitle = _eventUiState.value.title
                val newDescription = _eventUiState.value.description
                val eventStartTime = _eventUiState.value.startTime
                val eventEndTime = _eventUiState.value.endTime
                val eventStartDate = _eventUiState.value.startDate
                val eventEndDate = _eventUiState.value.endDate
                val eventNotificationTime = _eventUiState.value.remindAt

                if (newTitle.isEmpty() && newDescription.isEmpty()) {
                    return
                }

                if (_eventUiState.value.id.isEmpty()) {
                    _eventUiState.update {
                        it.copy(id = UUID.randomUUID().toString())
                    }
                }
                val newEventId = _eventUiState.value.id

                val newEvent = EventUiState(
                    id = newEventId,
                    title = newTitle,
                    description = newDescription,
                    startTime = eventStartTime,
                    endTime = eventEndTime,
                    startDate = eventStartDate,
                    endDate = eventEndDate,
                    remindAt = eventNotificationTime
                )
                viewModelScope.launch {
//                    reminderRepository.createNewReminder(
//
//                    )
                }
                _eventUiState.update {
                    it.copy(isEditingItem = false)
                }
            }

            is EventItemAction.SetTitle -> {
                viewModelScope.launch {
                    _eventUiState.update { it.copy(title = action.title) }
                }
            }

            is EventItemAction.SetDescription -> {
                _eventUiState.update { it.copy(description = action.description) }
            }

            is EventItemAction.SetReminderTime -> {
                _eventUiState.update { it.copy(remindAt = action.reminder) }
            }

            is EventItemAction.SaveSelectedPhotos -> {
                _eventUiState.update { it.copy(
                    photos = action.eventPhotos,
                ) }
            }

            is EventItemAction.SetEndDate -> {
                _eventUiState.update { it.copy(
                    endDate = action.endDate,
                    isEditingTime = false
                ) }
            }
            is EventItemAction.SetEndTime -> {
                _eventUiState.update { it.copy(
                    endTime = action.endTime,
                    isEditingTime = false
                ) }
            }
            is EventItemAction.SetStartDate -> {
                _eventUiState.update { it.copy(
                    startDate = action.startDate,
                    isEditingTime = false
                ) }
            }
            is EventItemAction.SetStartTime -> {
                _eventUiState.update { it.copy(
                    startTime = action.startTime,
                    isEditingTime = false
                ) }
            }

            EventItemAction.ShowDatePicker -> {
                _eventUiState.update { it.copy(isEditingDate = true) }
            }

            EventItemAction.HideDatePicker -> {
                _eventUiState.update {
                    it.copy(isEditingDate = false)
                }
            }

            EventItemAction.ShowReminderDropDown -> {
                _eventUiState.update {
                    it.copy(isEditingReminder = true)
                }
            }

            EventItemAction.HideReminderDropDown -> {
                _eventUiState.update {
                    it.copy(isEditingReminder = false)
                }
            }

            EventItemAction.ShowTimePicker -> {
                _eventUiState.update {
                    it.copy(isEditingTime = true)
                }
            }

            EventItemAction.HideTimePicker -> {
                _eventUiState.update {
                    it.copy(isEditingTime = false)
                }
            }

            EventItemAction.CloseEditDescriptionScreen -> {
                _eventUiState.update {
                    it.copy(isEditingItem = false)
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
