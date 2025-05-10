package com.example.taskyapplication.auth.domain

import com.example.taskyapplication.auth.data.AccessTokenResponse
import com.example.taskyapplication.auth.data.LoggedInUserResponse
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
    suspend fun requestAccessToken(refreshToken: String, userId: String): EmptyResult<DataError>
    suspend fun isTokenExpired(): EmptyResult<DataError>
    suspend fun logoutUser(): EmptyResult<DataError>
}

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val taskyApiService: TaskyApiService,
    private val authTokenManager: AuthTokenManager
) : AuthRepository {

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
        }.onSuccess { response ->
            authTokenManager.saveAuthInfo(
                LoggedInUserResponse(
                    fullName = response.fullName,
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    userId = response.userId,
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun requestAccessToken(
        refreshToken: String,
        userId: String
    ): EmptyResult<DataError> {
        val result = safeApiCall {
            taskyApiService.getNewAccessToken(
                refreshToken = refreshToken,
                userId = userId
            )
        }.onSuccess { response ->
            authTokenManager.updateAccessToken(
                AccessTokenResponse(
                    accessToken = response.accessToken,
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun isTokenExpired(): EmptyResult<DataError> {
        return safeApiCall {
            taskyApiService.authenticateUser()
        }.asEmptyDataResult()
    }

    override suspend fun logoutUser(): EmptyResult<DataError> {
        return safeApiCall {
            taskyApiService.logoutUser()
        }.onSuccess {
            authTokenManager.clearRefreshToken()
        }
    }
}
