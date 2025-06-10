package com.example.taskyapplication.agenda.items.main

import com.example.taskyapplication.agenda.items.main.data.AgendaSummaryMenuOption

sealed interface MainScreenAction {
    data object LogoutUser : MainScreenAction
    data class LaunchItemMenu(val option: AgendaSummaryMenuOption) : MainScreenAction
    data class SelectAgendaDate(val selectedDate: Long) : MainScreenAction
}