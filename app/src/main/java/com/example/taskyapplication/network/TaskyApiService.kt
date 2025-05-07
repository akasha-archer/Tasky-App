package com.example.taskyapplication.network

import com.example.taskyapplication.auth.data.AccessTokenResponse
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.UserLoginData
import com.example.taskyapplication.auth.domain.AccessTokenRequest
import com.example.taskyapplication.auth.domain.RegisterUserState
import com.example.taskyapplication.domain.util.DataError
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface TaskyApiService {

    @POST("/register")
    suspend fun registerUser(
        @Body fullName: String,
        @Body email: String,
        @Body password: String,
    )

    @POST("/login")
    suspend fun loginUser(
        @Body email: String,
        @Body password: String,
    ): Result<LoggedInUserResponse>


    @POST("/accessToken")
    suspend fun getNewAccessToken(
        @Body userAccessTokenRequest: AccessTokenRequest,
    ): Response<AccessTokenResponse>

    @GET("/authenticate")
    suspend fun authenticateUser(): Response<Int>


    @GET("/logout")
    suspend fun logoutUser()
}
