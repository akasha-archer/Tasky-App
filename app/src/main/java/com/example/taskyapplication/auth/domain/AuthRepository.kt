package com.example.taskyapplication.auth.domain

import com.example.taskyapplication.auth.data.LoggedInUserResponse
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.asEmptyDataResult
import com.example.taskyapplication.domain.utils.onSuccess
import com.example.taskyapplication.domain.utils.safeApiCall
import com.example.taskyapplication.network.AuthApiService
import com.example.taskyapplication.network.TokenRefreshApi
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepository {

    suspend fun fetchUserName(): String?
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
    private val authApiService: AuthApiService,
    private val tokenRefreshApi: TokenRefreshApi,
    private val authTokenManager: AuthTokenManager
) : AuthRepository {

    override suspend fun fetchUserName(): String? {
        return authTokenManager.readUserFullName()
    }

    override suspend fun registerNewUser(
        registerData: RegisterData
    ): EmptyResult<DataError> {
        return safeApiCall {
            authApiService.registerUser(
               registerData = registerData
            )
        }.asEmptyDataResult()
    }

    override suspend fun loginUser(
       loginData: LoginData
    ): EmptyResult<DataError> {
        val result = safeApiCall {
            authApiService.loginUser(
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

//    override suspend fun requestAccessToken(
//        tokenRequest: AccessTokenRequest
//    ): EmptyResult<DataError> {
//        val result = safeApiCall {
//            taskyApiService.getNewAccessToken(
//               accessTokenRequest = tokenRequest
//            )
//        }.onSuccess { response ->
//            authTokenManager.updateAccessToken(
//                AccessTokenResponse(
//                    newAccessToken = response.newAccessToken,
//                )
//            )
//        }
//        return result.asEmptyDataResult()
//    }

    override suspend fun requestAccessToken(
        tokenRequest: AccessTokenRequest
    ): EmptyResult<DataError> {
        return safeApiCall {
            tokenRefreshApi.getNewAccessToken(
                accessTokenRequest = tokenRequest
            )
        }.onSuccess { newTokensResponse ->
            authTokenManager.updateAccessToken(
                newTokensResponse.newAccessToken,
                newTokensResponse.expirationTimestamp
            )
        }.asEmptyDataResult()
    }

    override suspend fun authenticateToken(): EmptyResult<DataError> {
        return safeApiCall {
            authApiService.authenticateUser()
        }.asEmptyDataResult()
    }

//    override suspend fun authenticateToken(): EmptyResult<DataError> {
//        val result = safeApiCall {
//            taskyApiService.authenticateUser()
//        }
//        return result.asEmptyDataResult()
//    }

    override suspend fun logoutUser(): EmptyResult<DataError> {
        return safeApiCall {
            authApiService.logoutUser()
        }.onSuccess {
            authTokenManager.clearRefreshToken()
            // clear database tables
        }
    }
}
