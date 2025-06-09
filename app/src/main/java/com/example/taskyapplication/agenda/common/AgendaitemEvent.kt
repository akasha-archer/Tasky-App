package com.example.taskyapplication.agenda.common

sealed class AgendaItemEvent {
    data object NewItemCreatedSuccess : AgendaItemEvent()
    data object UpdateItemSuccess : AgendaItemEvent()
    data object DeleteSuccess : AgendaItemEvent()
    data class NewItemCreatedError(val errorMessage: String): AgendaItemEvent()
    data class UpdateItemError(val errorMessage: String): AgendaItemEvent()
    data class DeleteError(val errorMessage: String): AgendaItemEvent()

}