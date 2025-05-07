package com.example.taskyapplication.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.RegisterUserState
import com.example.taskyapplication.auth.domain.UserInputValidator
import com.example.taskyapplication.auth.login.LoginEvent
import com.example.taskyapplication.auth.presentation.utils.textAsFlow
import com.example.taskyapplication.auth.register.RegisterAction
import com.example.taskyapplication.auth.register.RegistrationEvent
import com.example.taskyapplication.domain.utils.Result
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

    private val _isTokenValid = MutableStateFlow(false)
    val isTokenValid = _isTokenValid.asStateFlow()

    val registrationEvents = MutableLiveData<RegistrationEvent>()
    val loginEvents = MutableLiveData<LoginEvent>()

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

    private fun registerNewUser() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = authRepository.registerNewUser(
                fullName = state.fullName.text.toString().trim(),
                email = state.email.text.toString().trim(),
                password = state.password.text.toString().trim()
            )
            state = state.copy(isRegistering = false)

            when (result) {
                is Result.Error -> {
                    registrationEvents.value =
                        RegistrationEvent.RegistrationError(result.error.toString())
                }
                is Result.Success -> {
                    registrationEvents.value = RegistrationEvent.RegistrationSuccess
                }
            }
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
//            state = state.copy(isRegistering = true) //LoginState object isLoggingIn = true
            val result = authRepository.loginUser(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString().trim()
            )
            //state = state.copy(isRegistering = false) //isLoggingIn = false
            when (result) {
                is Result.Error -> {
                    loginEvents.value =
                        LoginEvent.LoginError(result.error.toString())
                }
                is Result.Success -> {
                    loginEvents.value = LoginEvent.LoginSuccess
                }
            }
        }
    }

    fun registerActions(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> {} //register
            else -> Unit
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
