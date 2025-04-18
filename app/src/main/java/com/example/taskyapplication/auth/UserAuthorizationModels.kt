package com.example.taskyapplication.auth

data class User(
    val name: String,
    val initials: String,
    val email: String,
    val password: String,
    val isRegistered: Boolean = false,
    val isLoggedIn: Boolean
)

data class NewUserRegistrationData(
    val fullName: String,
    val email: String,
    val password: String
)

data class UserLoginData(
    val email: String,
    val password: String
)

data class LoggedInUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String,
    val accessTokenExpirationTimestamp: Long
)

data class UserAccessTokenRequest(
    val refreshToken: String,
    val userId: String
)

data class UserAccessTokenResponse(
    val accessToken: String,
    val expirationTimestamp: Long
)

// GET request to check authentication. Returns a 200 or 401
// GET request to log out