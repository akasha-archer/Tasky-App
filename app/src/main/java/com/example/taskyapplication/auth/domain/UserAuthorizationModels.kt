package com.example.taskyapplication.auth.domain

import kotlinx.serialization.Serializable

data class RegisterUserState(
    val fullName: String,
    val email: String,
    val password: String
)

data class AuthUserState(
    val fullName: String?,
    val userId: String?,
    val isRegistered: Boolean
)

// model for credentials sent to authenticate registered user
data class UserLoginData(
    val email: String,
    val password: String
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

data class PasswordValidationState(
    val isValid: Boolean = false,
    val errorMessage: String? = null
)

data class NameValidationState(
    val isValid: Boolean = false,
    val errorMessage: String? = null
)