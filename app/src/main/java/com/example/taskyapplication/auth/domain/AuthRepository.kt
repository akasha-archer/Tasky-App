package com.example.taskyapplication.auth.domain

import android.util.Log
import com.example.taskyapplication.auth.data.TaskyAppPreferences
import com.example.taskyapplication.remote.TaskyApiService
import kotlinx.serialization.SerializationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val taskyApiService: TaskyApiService,
    private val appPreferences: TaskyAppPreferences
) {
    suspend fun registerNewUser(
        userRegistrationData: NewUserRegistrationData
    ) {
        taskyApiService.registerUser(userRegistrationData)
    }

    suspend fun loginUser(
        userLoginData: UserLoginData
    ): LoggedInUserResponse? {
        return taskyApiService.loginUser(userLoginData).body()
    }

    suspend fun requestAccessToken(
        userRefreshTokenData: UserRefreshTokenData
    ): UserAccessTokenResponse? {
        return taskyApiService.getNewAccessToken(userRefreshTokenData).body()
    }

    suspend fun isTokenExpired(): Boolean {
        var responseCode: Int
        try {
             responseCode = taskyApiService.authenticateUser().code()
        } catch (e: SerializationException) {
            Log.e("Parse exception in AuthRepository", "Failed to read authentication response code: ${e.message}")
            throw e
        }
        return responseCode == 401
    }

    suspend fun logoutUser() {
        taskyApiService.logoutUser()
        appPreferences.deleteRefreshToken()
    }

    suspend fun saveRefreshToken(tokenResponse: LoggedInUserResponse) {
        appPreferences.saveAccessToken(tokenResponse.refreshToken)
    }

    suspend fun saveRegisteredUser(isRegistered: Boolean) {
        appPreferences.saveUserRegisteredState(isRegistered)
    }


}
