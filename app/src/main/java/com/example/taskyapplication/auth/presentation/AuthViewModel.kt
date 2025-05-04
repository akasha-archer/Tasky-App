package com.example.taskyapplication.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.AuthUserState
import com.example.taskyapplication.auth.domain.EmailValidationState
import com.example.taskyapplication.auth.domain.Lce
import com.example.taskyapplication.auth.domain.LoggedInUserResponse
import com.example.taskyapplication.auth.domain.NewUserRegistrationData
import com.example.taskyapplication.auth.domain.PasswordValidationState
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

//    // observe values from register screen to create object for API call
//    private val _userRegistrationViewState = MutableStateFlow<NewUserRegistrationData?>(null)
//    val userRegistrationViewState = _userRegistrationViewState.asStateFlow()

    private val _passwordValidationState = MutableStateFlow<PasswordValidationState?>(null)
    val passwordValidationState = _passwordValidationState.asStateFlow()

    private val _emailValidationState = MutableStateFlow<EmailValidationState?>(null)
    val emailValidationState = _emailValidationState.asStateFlow()

    fun updateUserName(name: String) {
        _authUserState.update { state ->
            state?.copy(fullName = name)
        }
    }

    fun updateUserEmail(email: String) {
        viewModelScope.launch {
            _authUserState.update { state ->
                state?.copy(email = email)
            }
        }
    }

    suspend fun updateUserPassword(password: String) {
        if (_passwordValidationState.value?.isValid == true) {
            _authUserState.update { state ->
                state?.copy(password = password)
            }
        }
    }

    suspend fun registerNewUser() =
        viewModelScope.launch {
            try {
                val registrationData = NewUserRegistrationData(
                    fullName = _authUserState.value?.fullName!!,
                    email = _authUserState.value?.email!!,
                    password = _authUserState.value?.password!!
                )
                authRepository.registerNewUser(registrationData)
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

    /**
     * Validates a password based on the following criteria:
     * - Minimum length of 9 characters.
     * - Contains at least one lowercase letter.
     * - Contains at least one uppercase letter.
     * - Contains at least one digit.
     *
     * @param password The password string to validate.
     * @return True if the password meets all criteria, false otherwise.
     */
    fun isPasswordValid(password: String) {
        var errorMessage: String? = null
        var isValid = true
        viewModelScope.launch {
            // Check 1: Minimum length
            if (password.length < 9) {
                errorMessage = "Password must be at least 9 characters long."
                isValid = !isValid
            }

            // Check 2: Contains at least one lowercase letter
            val hasLowercase = password.any { it.isLowerCase() }
            if (!hasLowercase) {
                errorMessage = "Password must contain at least one lowercase letter."
                isValid = !isValid
            }

            // Check 3: Contains at least one uppercase letter
            val hasUppercase = password.any { it.isUpperCase() }
            if (!hasUppercase) {
                errorMessage = "Password must contain at least one uppercase letter."
                isValid = !isValid
            }

            // Check 4: Contains at least one digit
            val hasDigit = password.any { it.isDigit() }
            if (!hasDigit) {
                errorMessage = "Password must contain at least one digit."
                isValid = !isValid
            }
        }
        _passwordValidationState.value = PasswordValidationState(isValid, errorMessage)
    }

    /**
     * Validates an email address based on basic criteria:
     * - Must contain the '@' symbol.
     * - Must have characters following the '@' symbol (representing the domain).
     *
     * @param email The email string to validate.
     * @return True if the email meets the basic criteria, false otherwise.
     */
    fun isEmailValid(email: String) {
        var errorMessage: String? = null
        var isValid = true
        viewModelScope.launch {
            // Check 1: Must contain '@'
            val atIndex = email.indexOf('@')
            if (atIndex == -1) {
                errorMessage = "Email must contain an '@' symbol."
                isValid = !isValid
            }

            // Check 2: Must have something after '@' (domain part)
            // Ensure '@' is not the last character in the string.
            if (atIndex == email.length - 1) {
                errorMessage = "Email must have a domain name after the '@' symbol."
                isValid = !isValid
            }

            // Check 3: Ensure there's something before '@' (local part)
            // Uncomment this block if you want to enforce having a local part.
            if (atIndex == 0) {
                errorMessage = "Email must have a local part before the '@' symbol."
                isValid = !isValid
            }
        }
        Log.d("EmailValidation", "Email: $email, isValid: $isValid, errorMessage: $errorMessage")
        _emailValidationState.value = EmailValidationState(isValid, errorMessage)
    }
}
