package com.example.taskyapplication.auth.register

sealed class RegistrationEvent {
    data class RegistrationError(val errorMessage: String): RegistrationEvent()
    data object RegistrationSuccess : RegistrationEvent()
}