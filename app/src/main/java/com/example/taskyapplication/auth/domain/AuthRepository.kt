package com.example.taskyapplication.auth.domain

import com.example.taskyapplication.auth.data.TaskyAppPreferences
import com.example.taskyapplication.remote.TaskyApiService
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
        val response = taskyApiService.authenticateUser()
        return response.code() == 401
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
