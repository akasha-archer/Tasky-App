package com.example.taskyapplication.agenda

import com.example.taskyapplication.agenda.data.model.ReminderOptions
import java.time.LocalDate
import java.time.LocalTime

sealed interface AgendaItemAction {

    data class EditExistingTask(val id: String): AgendaItemAction
    data class EditExistingReminder(val id: String): AgendaItemAction
    data class OpenExistingTask(val id: String): AgendaItemAction
    data class OpenExistingReminder(val id: String): AgendaItemAction
    data class SetTitle(val title: String): AgendaItemAction
    data class SetDescription(val description: String): AgendaItemAction
    data class SetTime(val time: LocalTime): AgendaItemAction
    data class SetDate(val date: LocalDate): AgendaItemAction
    data class SetReminderTime(val reminder: ReminderOptions): AgendaItemAction
    data class DeleteItem(val id: String): AgendaItemAction
    data object CloseDetailScreen: AgendaItemAction
    data object LaunchDateTimeEditScreen: AgendaItemAction
    data object CancelEdit: AgendaItemAction
    data object SaveDateTimeEdit: AgendaItemAction
    data object LaunchEditTitleScreen: AgendaItemAction
    data object CloseEditTitleScreen: AgendaItemAction
    data object LaunchEditDescriptionScreen: AgendaItemAction
    data object CloseEditDescriptionScreen: AgendaItemAction
    data object SaveAgendaItemUpdates: AgendaItemAction
    data object ShowTimePicker: AgendaItemAction
    data object HideTimePicker: AgendaItemAction
    data object ShowDatePicker: AgendaItemAction
    data object HideDatePicker: AgendaItemAction
    data object ShowReminderDropDown: AgendaItemAction
    data object HideReminderDropDown: AgendaItemAction
}