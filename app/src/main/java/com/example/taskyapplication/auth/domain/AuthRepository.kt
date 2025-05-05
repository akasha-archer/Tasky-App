package com.example.taskyapplication.auth.domain

import android.util.Log
import com.example.taskyapplication.auth.data.AccessTokenResponse
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import com.example.taskyapplication.auth.data.TaskyAppPreferences
import com.example.taskyapplication.network.TaskyApiService
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepository {
    suspend fun registerNewUser(userRegistrationData: RegisterUserState)
    suspend fun loginUser(userLoginData: UserLoginData): LoggedInUserResponse?
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
) : AuthRepository {

    override suspend fun registerNewUser(userRegistrationData: RegisterUserState) {
        taskyApiService.registerUser(userRegistrationData)
    }

    override suspend fun loginUser(userLoginData: UserLoginData): LoggedInUserResponse? {
        return taskyApiService.loginUser(userLoginData).body()
    }

    override suspend fun requestAccessToken(accessTokenRequest: AccessTokenRequest): AccessTokenResponse? {
        return taskyApiService.getNewAccessToken(accessTokenRequest).body()
    }

    override suspend fun isTokenExpired(): Boolean {
        var responseCode: Int
        try {
            responseCode = taskyApiService.authenticateUser().code()
        } catch (e: Exception) {
            Log.e("Parse exception in AuthRepository", "Failed to read authentication response code: ${e.message}")
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
