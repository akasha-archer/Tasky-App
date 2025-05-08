package com.example.taskyapplication.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.LoginUserState
import com.example.taskyapplication.auth.domain.UserInputValidator
import com.example.taskyapplication.auth.presentation.utils.textAsFlow
import com.example.taskyapplication.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val inputValidator: UserInputValidator
) : ViewModel() {

    var state by mutableStateOf(LoginUserState())
        private set
    val loginEvents = MutableLiveData<LoginEvent>()

    init {
        combine(state.email.textAsFlow(), state.password.textAsFlow()) { email, password ->
            state = state.copy(
                canLogin = inputValidator.isValidEmail(
                    email = email.toString().trim()
                ) && password.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when(action) {
            LoginAction.OnLoginClick -> loginUser()
            else -> Unit
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true) //LoginState object isLoggingIn = true
            val result = authRepository.loginUser(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString().trim()
            )
            state = state.copy(isLoggingIn = false) //isLoggingIn = false
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

    fun loginActions(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> loginUser()
            else -> Unit
        }
    }
}
