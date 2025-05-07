package com.example.taskyapplication.network

import com.example.taskyapplication.auth.data.AccessTokenResponse
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.UserLoginData
import com.example.taskyapplication.auth.domain.AccessTokenRequest
import com.example.taskyapplication.auth.domain.RegisterUserState
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface TaskyApiService {

    @POST("/register")
    suspend fun registerUser(
        @Body userRegistrationRequest: RegisterUserState
    )

    @POST("/login")
    suspend fun loginUser(
        @Body userLoginData: UserLoginData
    ): Response<LoggedInUserResponse>

    @POST("/accessToken")
    suspend fun getNewAccessToken(
        @Body userAccessTokenRequest: AccessTokenRequest,
    ): Response<AccessTokenResponse>

    @GET("/authenticate")
    suspend fun authenticateUser(): Response<Int>


    @GET("/logout")
    suspend fun logoutUser()
}
