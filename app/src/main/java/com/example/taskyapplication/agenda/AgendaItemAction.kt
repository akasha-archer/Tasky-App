package com.example.taskyapplication.agenda

sealed interface AgendaItemAction {
    data class SetTitle(val title: String): AgendaItemAction
    data class SetDescription(val description: String): AgendaItemAction
    data class SetTime(val time: String): AgendaItemAction
    data class SetDate(val date: String): AgendaItemAction
    data class SetReminderTime(val reminder: Long): AgendaItemAction
    data object LaunchEditTitleScreen: AgendaItemAction
    data object CloseEditTitleScreen: AgendaItemAction
    data object LaunchEditDescriptionScreen: AgendaItemAction
    data object CloseEditDescriptionScreen: AgendaItemAction
    data object SaveTaskUpdates: AgendaItemAction
    data object ShowTimePicker: AgendaItemAction
    data object HideTimePicker: AgendaItemAction
    data object ShowDatePicker: AgendaItemAction
    data object HideDatePicker: AgendaItemAction
    data object ShowReminderDropDown: AgendaItemAction
    data object HideReminderDropDown: AgendaItemAction
}