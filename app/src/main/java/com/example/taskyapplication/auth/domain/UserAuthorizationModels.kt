package com.example.taskyapplication.auth.domain

import androidx.compose.foundation.text.input.TextFieldState
import com.example.taskyapplication.agenda.domain.toInitials
import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(
    val fullName: String,
    val email: String,
    val password: String
)

@Serializable
data class LoginData(
    val email: String,
    val password: String
)

@Serializable
data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String
)

data class RegisterUserState(
    val fullName: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isNameValid: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
) {
    val userInitials: String
        get() = fullName.text.toString().toInitials()
}

data class LoginUserState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false
)
