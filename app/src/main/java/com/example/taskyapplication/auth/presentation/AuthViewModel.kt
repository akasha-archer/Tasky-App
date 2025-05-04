package com.example.taskyapplication.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.data.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.Lce
import com.example.taskyapplication.auth.domain.NameValidationState
import com.example.taskyapplication.auth.domain.PasswordValidationState
import com.example.taskyapplication.auth.domain.RegisterUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _isTokenValid = MutableStateFlow(false)
    val isTokenValid = _isTokenValid.asStateFlow()

    private val _nameValidationState = MutableStateFlow<NameValidationState?>(null)
    val nameValidationState = _nameValidationState.asStateFlow()

    private val _passwordValidationState = MutableStateFlow<PasswordValidationState?>(null)
    val passwordValidationState = _passwordValidationState.asStateFlow()

    private val _isNameInputError = MutableStateFlow(false)
    val isNameInputError = _isNameInputError.asStateFlow()

    private val _isEmailInputError = MutableStateFlow(false)
    val isEmailInputError = _isEmailInputError.asStateFlow()

    private val _isPasswordInputError = MutableStateFlow(false)
    val isPasswordInputError = _isPasswordInputError.asStateFlow()

    private val _registerUserState = MutableStateFlow(
            RegisterUserState(
                fullName = "",
                email = "",
                password = ""
            )
        )
    val registerUserState = _registerUserState.asStateFlow()

    // update registration state values
    fun setRegistrationName(name: String) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _registerUserState.update { it.copy(fullName = name) }
        }
    }

    fun setRegistrationEmail(email: String) {
        viewModelScope.launch(Dispatchers.Main.immediate)  {
            _registerUserState.update { it.copy(email = email) }
        }
    }

    fun setRegistrationPassword(password: String) {
        viewModelScope.launch(Dispatchers.Main.immediate)  {
            _registerUserState.update { it.copy(password = password) }
        }
    }

    suspend fun registerNewUser(registerData: RegisterUserState) =
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

    /**
     * Validates a password based on the following criteria:
     * - Minimum length of 9 characters.
     * - Contains at least one lowercase letter.
     * - Contains at least one uppercase letter.
     * - Contains at least one digit.
     *
     * @param password The password string to validate.
     * @return PasswordValidationState containing validation result and error message if invalid
     */
    fun validatePassword(password: String) {
        viewModelScope.launch {
            var errorMessage: String? = null
            var isValid = true

            // Check 1: Minimum length
            if (password.trim().length < 9) {
                errorMessage = "Password must be at least 9 characters long."
                isValid = false
            }
            else if (!password.any { it.isLowerCase() }) {
                // Check 2: Contains at least one lowercase letter
                errorMessage = "Password must contain at least one lowercase letter."
                isValid = false
            }
            else if (!password.any { it.isUpperCase() }) {
                // Check 3: Contains at least one uppercase letter
                errorMessage = "Password must contain at least one uppercase letter."
                isValid = false
            }
            else if (!password.any { it.isDigit() }) {
                // Check 4: Contains at least one digit
                errorMessage = "Password must contain at least one digit."
                isValid = false
            }
            _passwordValidationState.value = PasswordValidationState(isValid, errorMessage)
        }
    }

    /**
     * Validates a full name based on the following criteria:
     * - Contains at least a first name and last name separated by a space
     * - Maximum length of 50 characters
     * - Does not contain special characters or digits
     *
     * @param fullName The full name string to validate
     * @return NameValidationState containing validation result and error message if invalid
     */
    fun validateFullName(fullName: String) {
        viewModelScope.launch {
            var errorMessage: String? = null
            var isValid = true
            val trimmedName = fullName.trim()

            when {
                trimmedName.isEmpty() -> {
                    errorMessage = "Name cannot be empty."
                    isValid = false
                }
                trimmedName.length > 50 -> {
                    errorMessage = "Name cannot exceed 50 characters."
                    isValid = false
                }
                !trimmedName.contains(" ") -> {
                    errorMessage = "Please enter both first and last name."
                    isValid = false
                }
                !trimmedName.matches(Regex("^[a-zA-Z]+(\\s[a-zA-Z]+)+\$")) -> {
                    errorMessage = "Name should contain only letters and spaces."
                    isValid = false
                }
            }
            _nameValidationState.value = NameValidationState(isValid, errorMessage)
        }
    }

}
