package com.example.taskyapplication.auth.domain

import android.content.Context
import androidx.datastore.dataStore
import com.example.taskyapplication.auth.data.AccessTokenResponse
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore by dataStore(fileName = "auth_manager.json", serializer = AuthInfoSerializer)

interface AuthTokenManager {
    suspend fun saveAuthInfo(loggedInUserResponse: LoggedInUserResponse)
    suspend fun clearRefreshToken()
    suspend fun readAccessToken(): String
    suspend fun updateAccessToken(accessTokenResponse: AccessTokenResponse)
}

class AuthTokenManagerImpl @Inject constructor(
    private val context: Context
): AuthTokenManager {

    override suspend fun saveAuthInfo(loggedInUserResponse: LoggedInUserResponse) {
        context.dataStore.updateData { info ->
            info.copy(
                fullName = loggedInUserResponse.fullName,
                userId = loggedInUserResponse.userId,
                accessToken = loggedInUserResponse.accessToken,
                refreshToken = loggedInUserResponse.refreshToken
            )
        }
    }

    override suspend fun readAccessToken(): String {
        return context.dataStore.data.first().accessToken
    }

    override suspend fun updateAccessToken(accessTokenResponse: AccessTokenResponse) {
        context.dataStore.updateData { info ->
            info.copy(
                accessToken = accessTokenResponse.newAccessToken
            )
        }
    }

    override suspend fun clearRefreshToken() {
        context.dataStore.updateData { info ->
            info.copy(
                refreshToken = ""
            )
        }
    }
}