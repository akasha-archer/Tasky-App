package com.example.taskyapplication.auth.presentation

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskyapplication.R
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.auth.presentation.components.AuthScreenFooter
import com.example.taskyapplication.auth.presentation.components.AuthorizationCtaButton
import com.example.taskyapplication.auth.presentation.components.PasswordTextField
import com.example.taskyapplication.auth.presentation.components.UserInfoTextField
import com.example.taskyapplication.auth.presentation.utils.AuthScreenTitle
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun AccountCreationScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val registerState by authViewModel.registerUserState.collectAsState()
    val emailValidationState by authViewModel.emailValidationState.collectAsState()
    val nameValidationState by authViewModel.nameValidationState.collectAsState()
    val passwordValidationState by authViewModel.passwordValidationState.collectAsState()
    val autofillManager = LocalAutofillManager.current

    var hasEmailBeenFocused by remember {
        mutableStateOf(false)
    }
    var hasNameBeenFocused by remember {
        mutableStateOf(false)
    }
    var hasPasswordBeenFocused by remember {
        mutableStateOf(false)
    }
    TaskyBaseScreen(
        screenHeader = {
            AuthScreenTitle(
                modifier = modifier.padding(top = 24.dp),
                titleText = "Create Your Account"
            )
        },
        mainContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserInfoTextField(
                    userInput = registerState.fullName,
                    onUserInputChange = { value ->
                        authViewModel.setRegistrationName(value)
                        if (hasNameBeenFocused) {
                            authViewModel.validateFullName(value)
                        }
                    },
                    modifier = Modifier
                        .semantics { contentType = ContentType.PersonFullName }
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                hasNameBeenFocused = true
                            } else if (registerState.fullName.isNotEmpty()) {
                                // Validate when user leaves the field and it's not empty
                                authViewModel.validateFullName(registerState.fullName)
                            }
                        },
                    isError = nameValidationState?.isValid == false,
                    errorMessage = {
                        if (nameValidationState?.isValid == false) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = nameValidationState?.errorMessage ?: "",
                                color = taskyColors.error,
                                style = TaskyTypography.bodySmall
                            )
                        }
                    },
                    placeholderText = stringResource(R.string.register_name_placeholder),
                    onValidateInput = {
                        authViewModel.setRegistrationName(it)
                    },
                )
                UserInfoTextField(
                    userInput = registerState.email,
                    onUserInputChange = { value ->
                        authViewModel.setRegistrationEmail(value)
                        if (hasEmailBeenFocused) {
                            authViewModel.validateEmail(value)
                        }
                    },
                    modifier = Modifier
                        .semantics { contentType = ContentType.EmailAddress }
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                hasEmailBeenFocused = true
                            } else if (registerState.email.isNotEmpty()) {
                                // Validate when user leaves the field and it's not empty
                                authViewModel.validateEmail(registerState.email)
                            }
                        },
                    isError = emailValidationState == false,
                    errorMessage = {
                        if (emailValidationState == false) {
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
                        if (emailValidationState == false) {
                            authViewModel.setRegistrationEmail(it)
                        }
                    },
                )
                PasswordTextField(
                    userInput = registerState.password,
                    onUserInputChange = { value ->
                        authViewModel.setRegistrationPassword(value)
                        if (hasPasswordBeenFocused) {
                            authViewModel.validatePassword(value)
                        }
                    },
                    modifier = Modifier
                        .semantics { contentType = ContentType.NewPassword }
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                hasPasswordBeenFocused = true
                            } else if (registerState.password.isNotEmpty()) {
                                // Validate when user leaves the field and it's not empty
                                authViewModel.validatePassword(registerState.password)
                            }
                        },
                    isError = passwordValidationState?.isValid == false,
                    errorMessage = {
                        if (passwordValidationState?.isValid == false) {
                            Text(
                                text = passwordValidationState?.errorMessage ?: "",
                                color = taskyColors.error,
                                style = TaskyTypography.bodySmall
                            )
                        }
                    },
                    onValidatePassword = {
                        authViewModel.setRegistrationPassword(it)
                    },
                    placeholderText = stringResource(R.string.register_login_password_placeholder),
                )
                AuthorizationCtaButton(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    buttonText = "GET STARTED",
                    isButtonEnabled = ((nameValidationState?.isValid == false) ||
                            (nameValidationState?.isValid == false) ||
                            (passwordValidationState?.isValid == false)
                            ),
                    onButtonClick = {
                        autofillManager?.commit()
                        // call register endpoint

                        // if registration is successful (check state)
                        // navigate to log in screen
                    }
                )
                AuthScreenFooter(
                    navigateToScreen = navigateToLogin,
                    accountRegisteredPrompt = "Already have an account?",
                    loginOrSignupPrompt = "LOG IN"
                )
            }
        }
    )
}
