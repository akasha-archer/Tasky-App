package com.example.taskyapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.domain.AuthTokenManager
import com.example.taskyapplication.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authTokenManager: AuthTokenManager,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainViewState())
    val mainState = _mainState
        .onStart {
            checkUserRegisterStatus()
            authenticateUser()
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MainViewState()
        )

    private fun checkUserRegisterStatus() {
        viewModelScope.launch {
            _mainState.value = _mainState.value.copy(isValidatingUser = true)
            _mainState.value = _mainState.value.copy(
                isRegistered = authTokenManager.readAccessToken().isNotEmpty(),
            )
            _mainState.value = _mainState.value.copy(isValidatingUser = false)
        }
    }

    private fun authenticateUser() {
        viewModelScope.launch {
            val result = mainRepository.authenticateToken()
            _mainState.value = when (result) {
                is Result.Error -> {
                    _mainState.value.copy(isLoggedIn = false)
                }

                is Result.Success -> {
                    _mainState.value.copy(isLoggedIn = true)
                }
            }
        }
    }
}
