package com.example.taskyapplication.auth.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.data.TaskyAppPreferences
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.NewUserRegistrationData
import com.example.taskyapplication.auth.domain.UserLoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // save form data with savedStateHandle?

    suspend fun registerNewUser(registerData: NewUserRegistrationData) =
        viewModelScope.launch {
            try {
                authRepository.registerNewUser(registerData)
            } catch (e: Exception) {
                Log.e("New User Registration: ", "Failed to register user ${e.message}")
                throw e
            }
        }

    suspend fun loginUser(userData: UserLoginData): LoggedInUserResponse? {
        // login from text button on registration page OR splash screen
        // authenticate endpoint to check access token validity
        // check response > returns 200 or 401
        // if valid [200] > log in automatically > go to Agenda screen
        /*
        *  if (response.code == 401) {
        *  // request new token --> call /accessToken endpoint using refresh token
        * // write new token
        * }
        *
        * show log in screen using new accessToken
        * fetch new response and write new token
        * */
        var tokens: LoggedInUserResponse? = null
        viewModelScope.launch {
            try {
                tokens = authRepository.loginUser(userData)
            } catch (e: Exception) {
                Log.e("User Login: ", "Failed to log in user ${e.message}")
                throw e
            }
        }
        return tokens
    }

    suspend fun logOut() {
        authRepository.logoutUser()
        // show toast confirming log out
    }
}
