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
        registerData: RegisterData
    ): EmptyResult<DataError>

    suspend fun loginUser(
        loginData: LoginData
    ): EmptyResult<DataError>

    suspend fun requestAccessToken(tokenRequest: AccessTokenRequest): EmptyResult<DataError>
    suspend fun authenticateToken(): EmptyResult<DataError>
    suspend fun logoutUser(): EmptyResult<DataError>
}

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val taskyApiService: TaskyApiService,
    private val authTokenManager: AuthTokenManager
) : AuthRepository {

    override suspend fun registerNewUser(
        registerData: RegisterData
    ): EmptyResult<DataError> {
        return safeApiCall {
            taskyApiService.registerUser(
               registerData = registerData
            )
        }.asEmptyDataResult()
    }

    override suspend fun loginUser(
       loginData: LoginData
    ): EmptyResult<DataError> {
        val result = safeApiCall {
            taskyApiService.loginUser(
                loginData = loginData
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
        tokenRequest: AccessTokenRequest
    ): EmptyResult<DataError> {
        val result = safeApiCall {
            taskyApiService.getNewAccessToken(
               accessTokenRequest = tokenRequest
            )
        }.onSuccess { response ->
            authTokenManager.updateAccessToken(
                AccessTokenResponse(
                    newAccessToken = response.newAccessToken,
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun authenticateToken(): EmptyResult<DataError> {
        val result = safeApiCall {
            taskyApiService.authenticateUser()
        }
        return result.asEmptyDataResult()
    }

    override suspend fun logoutUser(): EmptyResult<DataError> {
        return safeApiCall {
            taskyApiService.logoutUser()
        }.onSuccess {
            authTokenManager.clearRefreshToken()
        }
    }
}
