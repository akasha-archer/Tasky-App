package com.example.taskyapplication.network

import android.util.Log
import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.auth.domain.AccessTokenRequest
import com.example.taskyapplication.auth.domain.AuthTokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import retrofit2.HttpException
import javax.inject.Provider


/**
 * Common interceptor used for all API calls except re-authentication
 */
class CommonInterceptor(
    val authTokenManager: AuthTokenManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val token = runBlocking {
            authTokenManager.readAccessToken()
        }
        val newRequest = request.newBuilder()
            .header("x-api-key", BuildConfig.API_KEY)
            .header("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(newRequest)
        when (response.code) {
            200 -> {
                Log.d("Tasky API 200 response", "$response")
            }
            400, 401, 403, 404 -> {
                Log.e("Tasky API ${response.code} error", "$response")
            }
            in 500..599 -> {
                Log.e("Tasky API server error: ${response.code}", "$response")
            }
        }
        return response
    }
}

/**
 * Interceptor only for user re-authentication. This is a separate
 * class so we avoid a circular dependency between the AuthAPI and an Interceptor
 */
class RefreshAuthenticationInterceptor(
    val authTokenManager: AuthTokenManager,
    private val tokenRefreshApiProvider: Provider<TokenRefreshApi>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request() // Renamed for clarity
        val token = runBlocking {
            authTokenManager.readAccessToken()
        }
        val requestWithAuth = originalRequest.newBuilder()
            .header("x-api-key", BuildConfig.API_KEY)
            .header("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(requestWithAuth)
        if (response.code != 401) {
            return response
        }
        // --- Token Refresh Logic ---
        synchronized(this) { // Synchronize to prevent multiple concurrent refresh attempts
            // Re-check if token was refreshed by another thread while waiting for synchronized block
            val currentTokenAfterSync = runBlocking { authTokenManager.readAccessToken() }
            if (token != currentTokenAfterSync) { // Token has been refreshed by another request
                response.close() // Close the old response
                val newRequestWithRefreshedToken = originalRequest.newBuilder()
                    .header("x-api-key", BuildConfig.API_KEY)
                    .header("Authorization", "Bearer $currentTokenAfterSync")
                    .build()
                return chain.proceed(newRequestWithRefreshedToken)
            }
            //token refresh
            return runBlocking {
                val refreshToken =
                    authTokenManager.readRefreshToken() // IMPORTANT: Use readRefreshToken()
                val userId = authTokenManager.readUserId()

                if (refreshToken == null || userId == null) {
                    // Cannot refresh, logout user or handle error
                    Log.e("RefreshAuth", "Refresh token or User ID is null. Cannot refresh.")
                    return@runBlocking response // Return original 401 response
                }

                val tokenRequest = AccessTokenRequest(
                    refreshToken = refreshToken,
                    userId = userId
                )

                val tokenRefreshApi =
                    tokenRefreshApiProvider.get() // Get the TokenRefreshApi instance HERE

                val tokenResponse = try {
                    Log.d("RefreshAuth", "Attempting to refresh token.")
                    tokenRefreshApi.getNewAccessToken(tokenRequest)
                } catch (e: IOException) {
                    Log.e("RefreshAuth", "IOException during token refresh: ${e.message}")
                    return@runBlocking response // Return original 401 response
                } catch (e: HttpException) {
                    Log.e(
                        "RefreshAuth",
                        "HttpException during token refresh: ${e.code()}, ${
                            e.response()?.errorBody()?.string()
                        }"
                    )
                    // If refresh itself fails with 401, it might mean refresh token is invalid -> logout
//                    if (e.code() == 401) {
//                        // authTokenManager.clearTokens() // Example: Clear tokens
//                        // Trigger logout flow
//                    }
                    return@runBlocking response // Return original 401 response
                }

                if (tokenResponse.isSuccessful && tokenResponse.body() != null) {
                    val newTokens = tokenResponse.body()!!
                    Log.d("RefreshAuth", "Token refreshed successfully.")
                    authTokenManager.updateAccessToken(
                        newToken = newTokens.newAccessToken,
                        expirationTimestamp = newTokens.expirationTimestamp
                    )
                    response.close() // Close the original response with 401

                    // Retry the original request with the new access token
                    val newRequestWithNewToken = originalRequest.newBuilder()
                        .header("x-api-key", BuildConfig.API_KEY)
                        .header("Authorization", "Bearer ${newTokens.newAccessToken}")
                        .build()
                    return@runBlocking chain.proceed(newRequestWithNewToken)
                } else {
                    Log.e(
                        "RefreshAuth",
                        "Token refresh API call failed or body was null. Code: ${tokenResponse.code()}"
                    )
                    // If refresh fails, return the original 401 response
                    return@runBlocking response
                }
            }
        }
    }
}