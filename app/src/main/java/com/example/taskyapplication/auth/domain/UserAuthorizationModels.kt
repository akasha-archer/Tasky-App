package com.example.taskyapplication.auth.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class AuthUserState(
    val fullName: String?,
    val userId: String?,
    val isRegistered: Boolean
) {
    fun userInitials(): String {
        return fullName?.split(" ")?.joinToString("") {
            it.first().toString()
        }!!
    }
}

@Parcelize
data class NewUserRegistrationData(
    val fullName: String,
    val email: String,
    val password: String
) : Parcelable

@Parcelize
data class UserLoginData(
    val email: String,
    val password: String
) : Parcelable

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

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
)