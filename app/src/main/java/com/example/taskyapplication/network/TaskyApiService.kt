package com.example.taskyapplication.network

import com.example.taskyapplication.auth.data.AccessTokenResponse
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.LoginData
import com.example.taskyapplication.auth.domain.RegisterData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskyApiService {

    @POST("/register")
    suspend fun registerUser(
        @Body registerData: RegisterData
    ): Response<Unit>

    @POST("/login")
    suspend fun loginUser(
        @Body loginData: LoginData
    ): Response<LoggedInUserResponse>

    @POST("/accessToken")
    suspend fun getNewAccessToken(
        @Body refreshToken: String,
        @Body userId: String,
    ): Response<AccessTokenResponse>

    @GET("/authenticate")
    suspend fun authenticateUser(): Response<Unit>

    @GET("/logout")
    suspend fun logoutUser(): Response<Unit>
}
