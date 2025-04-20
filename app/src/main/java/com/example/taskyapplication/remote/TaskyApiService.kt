package com.example.taskyapplication.remote

import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.auth.domain.AuthenticationResponse
import com.example.taskyapplication.auth.domain.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.NewUserRegistrationData
import com.example.taskyapplication.auth.domain.UserAccessTokenRequest
import com.example.taskyapplication.auth.domain.UserAccessTokenResponse
import com.example.taskyapplication.auth.domain.UserLoginData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface TaskyApiService {

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @POST("/register")
    suspend fun registerUser(
        @Body userRegistrationRequest: NewUserRegistrationData
    )

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @POST("/login")
    suspend fun loginExistingUser(
        @Body userLoginData: UserLoginData
    ): Response<LoggedInUserResponse>

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @POST("/accessToken")
    suspend fun getNewAccessToken(
        @Body userAccessTokenRequest: UserAccessTokenRequest,
    ): Response<UserAccessTokenResponse>

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("/authenticate")
    suspend fun authenticateUser(): Response<AuthenticationResponse>

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("/logout")
    suspend fun logoutUser()
}
