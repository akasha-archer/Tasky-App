package com.example.taskyapplication.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class LoggedInUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String,
    val accessTokenExpirationTimestamp: Long? = null
)

@Serializable
data class AccessTokenResponse(
    val newAccessToken: String,
    val expirationTimestamp: Long? = null
)

@Serializable
data class AuthenticationResponse(
    val responseCode: Int,
)
