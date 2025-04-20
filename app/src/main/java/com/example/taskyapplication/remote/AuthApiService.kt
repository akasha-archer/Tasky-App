package com.example.taskyapplication.remote

import com.example.taskyapplication.auth.domain.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.UserAccessTokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("/register")
    suspend fun registerUser(
        @Header("x-api-key") apiKey: String,
        @Body fullName: String,
        @Body email: String,
        @Body password: String
    )

    @POST("/login")
    suspend fun loginExistingUser(
        @Header("x-api-key") apiKey: String,
        @Body email: String,
        @Body password: String
    ): LoggedInUserResponse

    @POST("/accessToken")
    suspend fun getNewAccessToken(
        @Header("x-api-key") apiKey: String,
        @Body refreshToken: String,
        @Body userId: String
    ): UserAccessTokenResponse

    @GET("/authenticate")
    suspend fun authenticateUser(
        @Header("x-api-key") apiKey: String
    ): Result<String>

    @GET("/logout")
    suspend fun logoutUser(
        @Header("x-api-key") apiKey: String
    )
}
