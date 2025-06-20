package com.example.taskyapplication.agenda.items.event

import android.net.Uri
import com.example.taskyapplication.agenda.data.model.ReminderNotificationOption
import java.time.LocalDate
import java.time.LocalTime

sealed interface EventItemAction {
    data class OpenExistingEvent(val eventId: String): EventItemAction
    data class EditExistingEvent(val eventId: String): EventItemAction
    data class SetTitle(val title: String): EventItemAction
    data class SetDescription(val description: String): EventItemAction
    data class SetStartTime(val startTime: LocalTime): EventItemAction
    data class SetEndTime(val endTime: LocalTime): EventItemAction
    data class SetStartDate(val startDate: LocalDate): EventItemAction
    data class SetEndDate(val endDate: LocalDate): EventItemAction
    data class SetReminderTime(val reminder: ReminderNotificationOption): EventItemAction
    data class SaveSelectedPhotos(val eventPhotos: List<Uri>): EventItemAction
    data class DeleteEvent(val eventId: String): EventItemAction
    data class AddNewVisitor(val visitorEmail: String): EventItemAction
    data object CloseDetailScreen: EventItemAction
    data object LaunchDateTimeEditScreen: EventItemAction
    data object CancelEdit: EventItemAction
    data object SaveDateTimeEdit: EventItemAction
    data object LaunchEditTitleScreen: EventItemAction
    data object CloseEditTitleScreen: EventItemAction
    data object LaunchEditDescriptionScreen: EventItemAction
    data object CloseEditDescriptionScreen: EventItemAction
    data object SaveAgendaItemUpdates: EventItemAction
    data object ShowTimePicker: EventItemAction
    data object HideTimePicker: EventItemAction
    data object ShowDatePicker: EventItemAction
    data object HideDatePicker: EventItemAction
    data object ShowReminderDropDown: EventItemAction
    data object HideReminderDropDown: EventItemAction
}