package com.example.taskyapplication.auth.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.auth.domain.NewUserRegistrationData
import com.example.taskyapplication.auth.domain.UserLoginData
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskyapplication.auth.presentation.utils.ValidInputIcon

@Composable
fun AccountCreationContent(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userState by authViewModel.authUserState.collectAsState()
    val emailInputState by authViewModel.emailValidationState.collectAsState()
    val passwordInputState by authViewModel.passwordValidationState.collectAsState()

    // local mutable state initialized from the ViewModel
    var registrationName by rememberSaveable {
        mutableStateOf(userState?.fullName.orEmpty())
    }
    var registrationEmail by rememberSaveable {
        mutableStateOf(userState?.email.orEmpty())
    }
    var registrationPassword by rememberSaveable {
        mutableStateOf(userState?.password.orEmpty())
    }

    val showErrorMessage by rememberSaveable {
        mutableStateOf(emailInputState?.isValid == false || passwordInputState?.isValid == false)
    }

    var isButtonEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    val newUserData = NewUserRegistrationData(
        fullName = registrationName,
        email = registrationEmail,
        password = registrationPassword
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfoTextField(
            userInput = registrationName,
            onUserInputChange = {
                registrationName = it
            },
            errorMessage = {

            },
            placeholderText = stringResource(R.string.register_name_placeholder),
            onValidateInput = { },
            textFieldIcon = {
                ValidInputIcon()
            }
        )
        UserInfoTextField( // User's Email
            userInput = registrationEmail,
            onUserInputChange = {
                registrationEmail = it
                authViewModel.isEmailValid(registrationEmail)
            },
            errorMessage = {
                if (showErrorMessage) {
                    Text(
                        text = emailInputState?.errorMessage.orEmpty(),
                        color = taskyColors.error,
                        style = TaskyTypography.bodySmall
                    )
                }
            },
            placeholderText = stringResource(R.string.register_email_placeholder),
            keyboardType = KeyboardType.Email,
            onValidateInput = {
                authViewModel.isEmailValid(registrationEmail)
                if (emailInputState?.isValid == true) {
                    authViewModel.updateUserEmail(registrationEmail)
                }
            },
            textFieldIcon = {
                AnimatedVisibility(visible = !showErrorMessage) {
                    ValidInputIcon()
                }
            }
        )
        PasswordTextField(
            userInput = registrationPassword,
            onUserInputChange = {
                registrationPassword = it
            },
            onValidatePassword = {
                authViewModel.isPasswordValid(it)
            },
            errorMessage = {
                Text(
                    text = passwordInputState?.errorMessage ?: "",
                    color = taskyColors.error,
                    style = TaskyTypography.bodySmall
                )
            },
            placeholderText = stringResource(R.string.register_login_password_placeholder),
        )

        AuthorizationCtaButton(
            modifier = Modifier
                .padding(top = 16.dp),
            buttonText = "GET STARTED",
            isButtonEnabled = false,
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
            textFieldIcon = {},
            errorMessage = {},
        )
        PasswordTextField(
            userInput = passwordInput,
            onUserInputChange = {
                passwordInput = it
            },
            placeholderText = stringResource(id = R.string.register_login_password_placeholder),
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