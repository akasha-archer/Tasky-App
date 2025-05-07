package com.example.taskyapplication.auth.data

import com.example.taskyapplication.auth.domain.LoggedInUser
import kotlinx.serialization.Serializable

@Serializable
data class LoggedInUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String,
    val accessTokenExpirationTimestamp: Long
)

@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val expirationTimestamp: Long
)

// response after attempting to authenticate
@Serializable
data class ErrorResponse(
    val message: String
)

fun LoggedInUserResponse.toLoggedInUser(): LoggedInUser {
    return LoggedInUser(
        accessToken = accessToken,
        refreshToken = refreshToken,
        fullName = fullName,
        userId = userId,
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp
    )
}


