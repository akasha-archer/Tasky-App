package com.example.taskyapplication.auth.domain

import kotlinx.serialization.Serializable

data class User(
    val name: String,
    val initials: String,
    val isRegistered: Boolean = false,
)

@Serializable
data class NewUserRegistrationData(
    val fullName: String,
    val email: String,
    val password: String
)

@Serializable
data class UserLoginData(
    val email: String,
    val password: String
)

@Serializable
data class LoggedInUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String,
    val accessTokenExpirationTimestamp: Long
)

@Serializable
data class UserRefreshTokenData(
    val refreshToken: String,
    val userId: String
)

@Serializable
data class UserAccessTokenResponse(
    val accessToken: String,
    val expirationTimestamp: Long
)

@Serializable
data class AuthenticationResponse(
    val message: String
)