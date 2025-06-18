package com.example.taskyapplication.auth.domain

import android.content.Context
import androidx.datastore.dataStore
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore by dataStore(fileName = "auth_manager.json", serializer = AuthInfoSerializer)

interface AuthTokenManager {
    suspend fun saveAuthInfo(loggedInUserResponse: LoggedInUserResponse)
    suspend fun readUserFullName(): String?
    suspend fun clearRefreshToken()
    suspend fun readAccessToken(): String
    suspend fun readRefreshToken(): String
    suspend fun readUserId(): String
    suspend fun updateAccessToken(newToken: String, expirationTimestamp: Long)
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

    override suspend fun readUserFullName(): String? {
        return context.dataStore.data.first().fullName
    }

    override suspend fun readAccessToken(): String {
        return context.dataStore.data.first().accessToken
    }

    override suspend fun readRefreshToken(): String {
        return context.dataStore.data.first().refreshToken
    }

    override suspend fun readUserId(): String {
        return context.dataStore.data.first().userId
    }

    override suspend fun updateAccessToken(newToken: String, expirationTimestamp: Long) {
        context.dataStore.updateData { info ->
            info.copy(
                accessToken = newToken,
                tokenExpirationTimestamp = expirationTimestamp
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