package com.example.taskyapplication.auth.presentation

import androidx.lifecycle.ViewModel
import com.example.taskyapplication.auth.data.TaskyAppPreferences
import com.example.taskyapplication.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val appPreferences: TaskyAppPreferences
) : ViewModel() {

    private val _refreshToken = MutableStateFlow<String?>(null)
    val refreshToken = _refreshToken.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()


}