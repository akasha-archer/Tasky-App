package com.example.taskyapplication.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.RegisterData
import com.example.taskyapplication.auth.domain.RegisterUserState
import com.example.taskyapplication.auth.domain.UserInputValidator
import com.example.taskyapplication.auth.presentation.utils.textAsFlow
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val inputValidator: UserInputValidator
) : ViewModel() {

    private val eventChannel = Channel<RegistrationEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterUserState())
    val state = _state
        .onStart {
            collectNameUpdates()
            collectEmailUpdates()
            collectPasswordUpdates()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = RegisterUserState()
        )

    private fun collectNameUpdates() {
        viewModelScope.launch {
            _state.value.fullName.textAsFlow()
                .collect { name ->
                    val isNameValid = inputValidator.isNameValid(name.toString())
                    _state.value = _state.value.copy(
                        isNameValid = isNameValid,
                        canRegister = isNameValid && _state.value.isEmailValid
                                && _state.value.isPasswordValid && !_state.value.isRegistering
                    )
                }
        }
    }

    private fun collectEmailUpdates() {
        viewModelScope.launch {
            _state.value.email.textAsFlow()
                .collect { email ->
                    val isEmailValid = inputValidator.isValidEmail(email.toString())
                    _state.value = _state.value.copy(
                        isEmailValid = isEmailValid,
                        canRegister = isEmailValid && _state.value.isNameValid
                                && _state.value.isPasswordValid && !_state.value.isRegistering
                    )
                }
        }
    }

    private fun collectPasswordUpdates() {
        viewModelScope.launch {
            _state.value.password.textAsFlow()
                .collect { password ->
                    val isPasswordValid = inputValidator.isPasswordValid(password.toString())
                    _state.value = _state.value.copy(
                        isPasswordValid = isPasswordValid,
                        canRegister = isPasswordValid && _state.value.isEmailValid
                                && _state.value.isNameValid && !_state.value.isRegistering
                    )
                }
        }
    }

    private fun registerNewUser() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isRegistering = true)
            val result = authRepository.registerNewUser(
                registerData = RegisterData(
                    fullName = _state.value.fullName.text.toString().trim(),
                    email = _state.value.email.text.toString().trim(),
                    password = _state.value.password.text.toString().trim()
                ),
            )
            _state.value = _state.value.copy(isRegistering = false)
            when (result) {
                is Result.Error -> {
                    if(result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegistrationEvent.RegistrationError(
                            errorMessage = "User with this email already exists"
                        ))
                    } else {
                        eventChannel.send(RegistrationEvent.RegistrationError(
                            "Something went wrong. Please try again later."
                        )
                        )
                    }
                }
                is Result.Success -> {
                    eventChannel.send(RegistrationEvent.RegistrationSuccess)
                }
            }
        }
    }

    fun registerActions(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> registerNewUser()
            else -> Unit
        }
    }
}
