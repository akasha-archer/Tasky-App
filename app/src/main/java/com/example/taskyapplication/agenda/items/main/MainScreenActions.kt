package com.example.taskyapplication.agenda.items.main

import com.example.taskyapplication.agenda.data.model.AgendaItem
import com.example.taskyapplication.agenda.items.main.data.AgendaSummaryMenuOption

sealed interface MainScreenAction {
    data object LaunchDatePicker : MainScreenAction
    data object LaunchLogoutPopup : MainScreenAction
    data object LogoutUser : MainScreenAction
    data class NewItemPopup(val itemType: AgendaItem) : MainScreenAction
    data class LaunchItemMenu(val option: AgendaSummaryMenuOption) : MainScreenAction
}