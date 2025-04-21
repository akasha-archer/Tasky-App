package com.example.taskyapplication.auth.domain

import com.example.taskyapplication.remote.TaskyApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val taskyApiService: TaskyApiService
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

    suspend fun checkAuthentication(): String {
        return taskyApiService.authenticateUser().message()
    }

    suspend fun logoutUser() {
        taskyApiService.logoutUser()
    }

}
