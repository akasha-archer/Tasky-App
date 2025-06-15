package com.example.taskyapplication.auth.data

import kotlinx.serialization.SerialName
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
    @SerialName("accessToken")
    val newAccessToken: String,
    val expirationTimestamp: Long
)
