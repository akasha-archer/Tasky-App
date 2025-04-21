package com.example.taskyapplication.remote

import com.example.taskyapplication.auth.domain.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.NewUserRegistrationData
import com.example.taskyapplication.auth.domain.UserAccessTokenResponse
import com.example.taskyapplication.auth.domain.UserLoginData
import com.example.taskyapplication.auth.domain.UserRefreshTokenData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface TaskyApiService {

    @POST("/register")
    suspend fun registerUser(
        @Body userRegistrationRequest: NewUserRegistrationData
    )

    @POST("/login")
    suspend fun loginUser(
        @Body userLoginData: UserLoginData
    ): Response<LoggedInUserResponse>

    @POST("/accessToken")
    suspend fun getNewAccessToken(
        @Body userAccessTokenRequest: UserRefreshTokenData,
    ): Response<UserAccessTokenResponse>

    @GET("/authenticate")
    suspend fun authenticateUser(): Response<String>

    @GET("/logout")
    suspend fun logoutUser()
}
