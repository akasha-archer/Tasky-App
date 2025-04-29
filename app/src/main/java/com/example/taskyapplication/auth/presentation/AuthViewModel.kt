package com.example.taskyapplication.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.AuthUserState
import com.example.taskyapplication.auth.domain.Lce
import com.example.taskyapplication.auth.domain.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.NewUserRegistrationData
import com.example.taskyapplication.auth.domain.UserLoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _lceAuthUserData = MutableStateFlow<Lce<LoggedInUserResponse?>>(Lce.Loading)
    val lceAuthUserData = _lceAuthUserData.asStateFlow()

    private val _authUserState = MutableStateFlow<AuthUserState?>(null)
    val authUserState = _authUserState.asStateFlow()

    private val _isTokenValid = MutableStateFlow(false)
    val isTokenValid = _isTokenValid.asStateFlow()

    suspend fun registerNewUser(registerData: NewUserRegistrationData) =
        viewModelScope.launch {
            try {
                authRepository.registerNewUser(registerData)
            } catch (e: Exception) {
                Log.e("New User Registration: ", "Failed to register user ${e.message}")
                throw e
            }
        }

    suspend fun login(userData: UserLoginData) {
        _lceAuthUserData.value = Lce.Loading
        viewModelScope.launch {
            try {
                val loginResponse = authRepository.loginUser(userData)
                _lceAuthUserData.value = Lce.Success(loginResponse)

                if (loginResponse != null) {
                    // Update the auth user state
                    _authUserState.update { state ->
                        state?.copy(
                            userId = loginResponse.userId,
                            fullName = loginResponse.fullName,
                        )
                    }
                    // save refresh token and registered status
                    authRepository.saveRefreshToken(loginResponse)
                    authRepository.saveRegisteredUser(loginResponse.userId.isNotEmpty())
                }
            } catch (e: Exception) {
                _lceAuthUserData.value = Lce.Error(e)
                Log.e("User Login: ", "Failed to log in user ${e.message}")
                throw e
            }
        }
    }

     fun isTokenExpired() {
        viewModelScope.launch {
            _isTokenValid.value = authRepository.isTokenExpired()
        }
    }

    suspend fun logOut() {
        authRepository.logoutUser()
    }
}
