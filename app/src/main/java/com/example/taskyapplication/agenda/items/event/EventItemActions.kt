package com.example.taskyapplication.agenda.items.event

import android.net.Uri
import com.example.taskyapplication.agenda.data.model.ReminderOptions

sealed interface EventItemAction {
    data class SetTitle(val title: String): EventItemAction
    data class SetDescription(val description: String): EventItemAction
    data class SetStartTime(val startTime: String): EventItemAction
    data class SetEndTime(val endTime: String): EventItemAction
    data class SetStartDate(val startDate: String): EventItemAction
    data class SetEndDate(val endDate: String): EventItemAction
    data class SetReminderTime(val reminder: ReminderOptions): EventItemAction
    data class SaveSelectedPhotos(val eventPhotos: List<Uri>): EventItemAction
    data class DeleteEvent(val eventId: String): EventItemAction
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