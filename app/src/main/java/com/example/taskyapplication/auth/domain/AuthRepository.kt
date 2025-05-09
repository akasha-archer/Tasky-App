package com.example.taskyapplication.auth.domain

import android.util.Log
import com.example.taskyapplication.auth.data.AccessTokenResponse
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import com.example.taskyapplication.auth.data.TaskyAppPreferences
import com.example.taskyapplication.domain.utils.BaseRepository
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.asEmptyDataResult
import com.example.taskyapplication.domain.utils.onSuccess
import com.example.taskyapplication.domain.utils.safeApiCall
import com.example.taskyapplication.network.TaskyApiService
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepository {
    suspend fun registerNewUser(
        fullName: String,
        email: String,
        password: String
    ): EmptyResult<DataError>

    suspend fun loginUser(email: String, password: String): EmptyResult<DataError>
    suspend fun requestAccessToken(accessTokenRequest: AccessTokenRequest): AccessTokenResponse?
    suspend fun isTokenExpired(): Boolean
    suspend fun logoutUser()
    suspend fun saveRefreshToken(tokenResponse: LoggedInUserResponse)
    suspend fun saveRegisteredUser(isRegistered: Boolean)
}

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val taskyApiService: TaskyApiService,
    private val appPreferences: TaskyAppPreferences
) : BaseRepository(), AuthRepository {

    override suspend fun registerNewUser(
        fullName: String,
        email: String,
        password: String
    ): EmptyResult<DataError> {
        return safeApiCall {
            taskyApiService.registerUser(
                fullName = fullName,
                email = email,
                password = password
            )
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): EmptyResult<DataError> {
        val result = safeApiCall {
            taskyApiService.loginUser(
                email = email,
                password = password
            )
        }.onSuccess {
            TODO("save tokens and user info")
        }
        return result.asEmptyDataResult()
    }

    override suspend fun requestAccessToken(accessTokenRequest: AccessTokenRequest): AccessTokenResponse? {
        return taskyApiService.getNewAccessToken(accessTokenRequest).body()
    }

    override suspend fun isTokenExpired(): Boolean {
        var responseCode: Int
        try {
            responseCode = taskyApiService.authenticateUser().code()
        } catch (e: Exception) {
            Log.e(
                "Parse exception in AuthRepository",
                "Failed to read authentication response code: ${e.message}"
            )
            throw e
        }
        return responseCode == 401
    }

    override suspend fun logoutUser() {
        taskyApiService.logoutUser()
        appPreferences.deleteRefreshToken()
    }

    override suspend fun saveRefreshToken(tokenResponse: LoggedInUserResponse) {
        appPreferences.saveAccessToken(tokenResponse.refreshToken)
    }

    override suspend fun saveRegisteredUser(isRegistered: Boolean) {
        appPreferences.saveUserRegisteredState(isRegistered)
    }
}
