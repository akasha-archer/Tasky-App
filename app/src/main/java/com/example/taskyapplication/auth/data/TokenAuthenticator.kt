package com.example.taskyapplication.auth.data

import com.example.taskyapplication.auth.domain.LoggedInUserResponse
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val appPreferences: TaskyAppPreferences
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("Not yet implemented")
    }

   suspend fun refreshAccessToken() {
       // api call to /accessToken endpoint
       // pass refresh token and user id
       // response is UserAccessTokenResponse
       // save token from response


   }

    suspend fun saveAccessToken(tokenResponse: LoggedInUserResponse) {
        appPreferences.saveAuthToken(tokenResponse.accessToken)
    }

}