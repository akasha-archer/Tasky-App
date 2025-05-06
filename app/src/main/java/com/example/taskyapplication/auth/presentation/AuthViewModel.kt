package com.example.taskyapplication.auth.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.Lce
import com.example.taskyapplication.auth.domain.RegisterUserState
import com.example.taskyapplication.auth.domain.UserInputValidator
import com.example.taskyapplication.auth.presentation.utils.textAsFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val inputValidator: UserInputValidator
) : ViewModel() {

    private val _lceAuthUserData = MutableStateFlow<Lce<LoggedInUserResponse?>>(Lce.Loading)
    val lceAuthUserData = _lceAuthUserData.asStateFlow()

    private val _isTokenValid = MutableStateFlow(false)
    val isTokenValid = _isTokenValid.asStateFlow()

    var state by mutableStateOf(RegisterUserState())
        private set

    init {
        // save input values as snapshot flows
        state.fullName.textAsFlow()
            .onEach { name ->
                val nameValidationState = inputValidator.validateFullName(name.toString())
                state = state.copy(
                    nameValidationState = nameValidationState,
                    canRegister = nameValidationState.isValid && state.isEmailValid
                            && state.passwordValidationState.isValidPassword && !state.isRegistering
                )
            }

        state.email.textAsFlow()
            .onEach { email ->
                val isEmailValid = inputValidator.isValidEmail(email.toString())
                state = state.copy(
                    isEmailValid = isEmailValid,
                    canRegister = isEmailValid && state.nameValidationState.isValid
                            && state.passwordValidationState.isValidPassword && !state.isRegistering
                )
            }

        state.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState = inputValidator.validatePassword(password.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = passwordValidationState.isValidPassword && state.isEmailValid
                            && state.nameValidationState.isValid && !state.isRegistering
                )
            }
    }

    fun registerNewUser(registerData: RegisterUserState) =
        viewModelScope.launch {
            try {
                authRepository.registerNewUser(registerData)
            } catch (e: Exception) {
                Log.e("New User Registration: ", "Failed to register user ${e.message}")
                throw e
            }
        }

//    suspend fun login(userData: UserLoginData) {
//        _lceAuthUserData.value = Lce.Loading
//        viewModelScope.launch {
//            try {
//                val loginResponse = authRepository.loginUser(userData)
//                _lceAuthUserData.value = Lce.Success(loginResponse)
//
//                if (loginResponse != null) {
//                    // Update the auth user state
//                    _authUserState.update { state ->
//                        state?.copy(
//                            userId = loginResponse.userId,
//                            fullName = loginResponse.fullName,
//                        )
//                    }
//                    // save refresh token and registered status
//                    authRepository.saveRefreshToken(loginResponse)
//                    authRepository.saveRegisteredUser(loginResponse.userId.isNotEmpty())
//                }
//            } catch (e: Exception) {
//                _lceAuthUserData.value = Lce.Error(e)
//                Log.e("User Login: ", "Failed to log in user ${e.message}")
//                throw e
//            }
//        }
//    }

    fun isTokenExpired() {
        viewModelScope.launch {
            _isTokenValid.value = authRepository.isTokenExpired()
        }
    }

    suspend fun logOut() {
        authRepository.logoutUser()
    }
}
