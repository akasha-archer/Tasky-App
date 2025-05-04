package com.example.taskyapplication.auth.presentation

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.auth.domain.UserLoginData
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AccountCreationContent(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val passwordValidation = authViewModel.passwordValidationState.collectAsState()
    val nameValidation = authViewModel.nameValidationState.collectAsState()

    var registrationName by remember {
        mutableStateOf("")
    }
    var registrationEmail by remember {
        mutableStateOf("")
    }
    var registrationPassword by remember {
        mutableStateOf("")
    }

    var isEmailInputError by remember {
        mutableStateOf(false)
    }

    var hasEmailBeenFocused by remember {
        mutableStateOf(false)
    }

    var isNameInputError by remember {
        mutableStateOf(false)
    }

    var hasNameBeenFocused by remember {
        mutableStateOf(false)
    }

    var isPasswordInputError by remember {
        mutableStateOf(passwordValidation.value?.isValid == false)
    }

    var hasPasswordBeenFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfoTextField(  // User Name
            userInput = registrationName,
            onUserInputChange = {
                registrationName = it
                if (hasNameBeenFocused) {
                    authViewModel.validateFullName(it)
                    isNameInputError = nameValidation.value?.isValid == false
                }
            },
            modifier = Modifier
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        hasNameBeenFocused = true
                    } else if (registrationName.isNotEmpty()) {
                        // Validate when user leaves the field and it's not empty
                        authViewModel.validateFullName(registrationName)
                        isNameInputError = nameValidation.value?.isValid == false
                    }
                },
            isError = isNameInputError,
            errorMessage = {
                if (isNameInputError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = nameValidation.value?.errorMessage ?: "",
                        color = taskyColors.error,
                        style = TaskyTypography.bodySmall
                    )
                }
            },
            placeholderText = stringResource(R.string.register_name_placeholder),
            onValidateInput = { input ->
                authViewModel.updateUserName(input)
            },
        )
        UserInfoTextField( // User's Email
            userInput = registrationEmail,
            onUserInputChange = {
                registrationEmail = it
//                if (hasEmailBeenFocused) {
//                    isEmailInputError =
//                        Patterns.EMAIL_ADDRESS.matcher(registrationEmail).matches().not()
//                }
            },
            modifier = Modifier
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        hasEmailBeenFocused = true
                    } else if (registrationEmail.isNotEmpty()) {
                        isEmailInputError =
                            Patterns.EMAIL_ADDRESS.matcher(registrationEmail).matches().not()
                    }
//                    if (!focusState.isFocused && registrationEmail.isNotEmpty()) {
//                        isEmailInputError =
//                            Patterns.EMAIL_ADDRESS.matcher(registrationEmail).matches().not()
//                    }
                },
            isError = isEmailInputError,
            errorMessage = {
                if (isEmailInputError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Please enter a valid email address",
                        color = taskyColors.error,
                        style = TaskyTypography.bodySmall
                    )
                }
            },
            placeholderText = stringResource(R.string.register_email_placeholder),
            keyboardType = KeyboardType.Email,
            onValidateInput = {
                if (!isEmailInputError) {
                    authViewModel.updateUserEmail(registrationEmail)
                }
            },
        )
        PasswordTextField(  // User Password
            userInput = registrationPassword,
            onUserInputChange = {
                registrationPassword = it
                if (hasPasswordBeenFocused) {
                    authViewModel.validatePassword(it)
                    isPasswordInputError = passwordValidation.value?.isValid == false
                }
            },
            modifier = Modifier
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        hasPasswordBeenFocused = true
                    } else if (registrationPassword.isNotEmpty()) {
                        // Validate when user leaves the field and it's not empty
                        authViewModel.validatePassword(registrationPassword)
                        isPasswordInputError = passwordValidation.value?.isValid == false
                    }
                },
            isError = isPasswordInputError,
            errorMessage = {
                if (isPasswordInputError) {
                    Text(
                        text = passwordValidation.value?.errorMessage ?: "",
                        color = taskyColors.error,
                        style = TaskyTypography.bodySmall
                    )
                }
            },
            onValidatePassword = { input ->
                    authViewModel.updateUserPassword(input)
            },
            placeholderText = stringResource(R.string.register_login_password_placeholder),
        )
        AuthorizationCtaButton(
            modifier = Modifier
                .padding(top = 16.dp),
            buttonText = "GET STARTED",
            isButtonEnabled = true,
            onButtonClick = {
                onRegisterClick()
                // navigate to log in screen
            }
        )
        AuthScreenFooter(
            navigateToScreen = {
                // navigate to log in screen
            },
            accountRegisteredPrompt = "Already have an account?",
            loginOrSignupPrompt = "LOG IN"
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onLoginClick: (UserLoginData) -> Unit = {},
) {
    var emailInput by rememberSaveable {
        mutableStateOf("")
    }

    var passwordInput by rememberSaveable {
        mutableStateOf("")
    }

    val userLoginData by rememberSaveable {
        mutableStateOf(
            UserLoginData(
                email = emailInput,
                password = passwordInput
            )
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfoTextField(
            userInput = emailInput,
            onUserInputChange = {
                emailInput = it
            },
            placeholderText = stringResource(id = R.string.login_email_placeholder),
            keyboardType = KeyboardType.Email,
            onValidateInput = {},
            isError = false,
            errorMessage = {},
        )
        PasswordTextField(
            userInput = passwordInput,
            onUserInputChange = {
                passwordInput = it
            },
            isError = false,
            placeholderText = stringResource(id = R.string.register_login_password_placeholder),
            errorMessage = {},
        )
        AuthorizationCtaButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "LOG IN",
            onButtonClick = {
                onLoginClick(userLoginData)
                // navigate to agenda
            }
        )
        AuthScreenFooter(
            navigateToScreen = {
                // navigate to register screen
            },
            accountRegisteredPrompt = "Don't have an account?  ",
            loginOrSignupPrompt = "Sign up"
        )
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun AuthenticationScreenPreview() {
//    AccountCreationContent(
//        onRegisterClick = {}
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RegisteredUserScreenPreview() {
//    LoginContent(
//        onLoginClick = {}
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RegisterPreview() {
//    UserRegistrationScreen()
//}
//
//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    UserLoginScreen()
//}