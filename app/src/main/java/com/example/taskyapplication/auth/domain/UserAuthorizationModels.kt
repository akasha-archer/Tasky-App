package com.example.taskyapplication.auth.domain

import androidx.compose.foundation.text.input.TextFieldState

data class RegisterUserState(
    val fullName: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isNameValid: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)

data class LoginUserState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false
)