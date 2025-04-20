package com.example.taskyapplication.auth.domain

import androidx.lifecycle.ViewModel
import com.example.taskyapplication.TaskyAppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
//    private val userRepository: UserRepository,
    private val appPreferences: TaskyAppPreferences
): ViewModel() {


}