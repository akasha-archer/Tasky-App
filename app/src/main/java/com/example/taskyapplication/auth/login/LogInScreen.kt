package com.example.taskyapplication.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.auth.domain.LoginUserState
import com.example.taskyapplication.auth.presentation.components.AuthCtaButton
import com.example.taskyapplication.auth.presentation.components.AuthScreenFooter
import com.example.taskyapplication.auth.presentation.components.BaseInputField
import com.example.taskyapplication.auth.presentation.components.PasswordTextField
import com.example.taskyapplication.auth.presentation.utils.AuthScreenTitle
import com.example.taskyapplication.domain.utils.ObserveAsEvents

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit = {},
    onLoginSuccess: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(loginViewModel.events) { event ->
        when (event) {
            is LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    "Login Successful!",
                    Toast.LENGTH_SHORT
                ).show()
                onLoginSuccess()
            }

            is LoginEvent.LoginError -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    TaskyBaseScreen(
        screenHeader = {
            AuthScreenTitle(
                modifier = modifier.padding(top = 24.dp),
                titleText = "Welcome Back!"
            )
        },
        mainContent = {
            LoginScreen(
                state = loginState,
                onAction = { action ->
                    when (action) {
                        LoginAction.OnSignUpClick -> onSignUpClick()
                        else -> Unit
                    }
                    loginViewModel.loginActions(action)
                }
            )
        }
    )
}


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginUserState,
    onAction: (LoginAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseInputField(
            state = state.email,
            isValid = true,
            hintText = "Email address",
            keyboardType = KeyboardType.Email,
            textFieldIcon = null,
        )
        PasswordTextField(
            state = state.password,
            isPasswordValid = true,
        )
        AuthCtaButton(
            modifier = Modifier
                .padding(top = 16.dp),
            buttonText = "Log in",
            isButtonEnabled = state.canLogin && !state.isLoggingIn,
            isLoading = state.isLoggingIn,
            onButtonClick = {
                onAction(LoginAction.OnLoginClick)
            }
        )
        AuthScreenFooter(
            navigateToScreen = { onAction(LoginAction.OnSignUpClick) },
            accountRegisteredPrompt = "Don't have an account?",
            loginOrSignupPrompt = "Sign Up"
        )
    }
}
