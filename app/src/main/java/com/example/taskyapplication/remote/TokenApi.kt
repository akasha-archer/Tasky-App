package com.example.taskyapplication.remote

import com.example.taskyapplication.auth.domain.UserAccessTokenResponse
import com.example.taskyapplication.auth.domain.UserRefreshTokenData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TokenApi {

    @POST("/accessToken")
    suspend fun getNewAccessToken(
        @Body userAccessTokenRequest: UserRefreshTokenData,
    ): Response<UserAccessTokenResponse>

    @GET("/authenticate")
    suspend fun authenticateUser(): Response<String>

}