package com.example.taskyapplication.network

import com.example.taskyapplication.auth.data.AccessTokenResponse
import com.example.taskyapplication.auth.domain.AccessTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenRefreshApi {

    @POST("accessToken")
    suspend fun getNewAccessToken(
        @Body accessTokenRequest: AccessTokenRequest,
    ): Response<AccessTokenResponse>
}