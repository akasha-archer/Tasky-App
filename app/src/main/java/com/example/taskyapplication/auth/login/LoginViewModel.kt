package com.example.taskyapplication.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.LoginData
import com.example.taskyapplication.auth.domain.LoginUserState
import com.example.taskyapplication.auth.domain.UserInputValidator
import com.example.taskyapplication.auth.presentation.utils.textAsFlow
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val inputValidator: UserInputValidator
) : ViewModel() {

    private val _isTokenValid = MutableStateFlow(false)
    val isTokenValid = _isTokenValid.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(LoginUserState())
    val state = _state
        .onStart {
            collectLoginUpdates()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = LoginUserState()
        )

    private fun collectLoginUpdates() {
        viewModelScope.launch {
            combine(_state.value.email.textAsFlow(), _state.value.password.textAsFlow()) { email, password ->
                _state.value = _state.value.copy(
                    canLogin = inputValidator.isValidEmail(
                        email = email.toString().trim()
                    ) && password.isNotEmpty()
                )
            }.launchIn(viewModelScope)
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoggingIn = true) //LoginState object isLoggingIn = true
            val result = authRepository.loginUser(
                loginData = LoginData(
                    email = _state.value.email.text.toString().trim(),
                    password = _state.value.password.text.toString().trim()
                )
            )
            _state.value = _state.value.copy(isLoggingIn = false) //isLoggingIn = false
            when (result) {
                is Result.Error -> {
                    if(result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(LoginEvent.LoginError(
                            errorMessage = "Invalid email or password"
                        ))
                    } else {
                        eventChannel.send(LoginEvent.LoginError(
                            errorMessage = "An error occurred while logging in"
                        ))
                    }
                }
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }

    fun isTokenExpired() {
        viewModelScope.launch {
            val result = authRepository.authenticateToken()
            when (result) {
                is Result.Error -> {
                    _isTokenValid.value = false
                }
                is Result.Success -> {
                    _isTokenValid.value = true
                }
            }
        }
    }

    suspend fun logOut() {
        authRepository.logoutUser()
    }

    fun loginActions(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> loginUser()
            else -> Unit
        }
    }
}
