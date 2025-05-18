package com.example.taskyapplication.auth.domain

import kotlinx.serialization.Serializable

@Serializable
data class AuthInfo(
    val fullName: String = "",
    val userId: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
    val tokenExpirationTimestamp: Long = 0L
)