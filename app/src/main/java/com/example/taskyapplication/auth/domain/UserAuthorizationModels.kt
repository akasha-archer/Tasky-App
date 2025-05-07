package com.example.taskyapplication.auth.domain

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.serialization.Serializable

data class RegisterUserState(
    val fullName: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val nameValidationState: NameValidationState = NameValidationState(),
    val isEmailValid: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)
data class LoginUserState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false
)

data class AuthUserState(
    val fullName: String?,
    val userId: String?,
    val isRegistered: Boolean
)

// model for the response for a logged in user
data class LoggedInUser(
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String,
    val accessTokenExpirationTimestamp: Long
)

// model for requesting a new access token
@Serializable
data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String
)

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
)
