package com.example.taskyapplication.auth.login

sealed class LoginEvent {
    data class LoginError(val errorMessage: String): LoginEvent()
    data object LoginSuccess : LoginEvent()
}
