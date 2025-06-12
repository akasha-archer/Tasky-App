package com.example.taskyapplication.agenda.items.main

import com.example.taskyapplication.agenda.items.main.data.AgendaItemType
import java.time.LocalDate

sealed interface MainScreenAction {
    data object LogoutUser : MainScreenAction
//    data class SelectMenuItem(val action: AgendaSummaryMenuAction) : MainScreenAction
    data class SelectAgendaDate(val selectedDate: LocalDate) : MainScreenAction
    data class ItemToOpen(val itemId: String, val type: AgendaItemType) : MainScreenAction
    data class ItemToEdit(val itemId: String, val type: AgendaItemType) : MainScreenAction
    data class ItemToDelete(val itemId: String, val type: AgendaItemType) : MainScreenAction
}