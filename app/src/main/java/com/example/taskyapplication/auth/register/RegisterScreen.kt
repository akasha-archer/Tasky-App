package com.example.taskyapplication.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.auth.domain.RegisterUserState
import com.example.taskyapplication.auth.presentation.components.AuthScreenFooter
import com.example.taskyapplication.auth.presentation.components.AuthCtaButton
import com.example.taskyapplication.auth.presentation.components.BaseInputField
import com.example.taskyapplication.auth.presentation.components.PasswordTextField
import com.example.taskyapplication.auth.presentation.utils.AuthScreenTitle
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onRegisterSuccess: () -> Unit,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val events = registerViewModel.registrationEvents.observeAsState()

    events.value?.let { event ->
        when (event) {
            is RegistrationEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    "Registration Successful! You can now log in.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is RegistrationEvent.RegistrationError -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
                onRegisterSuccess()
            }
        }
    }

    TaskyBaseScreen(
        screenHeader = {
            AuthScreenTitle(
                modifier = modifier.padding(top = 24.dp),
                titleText = "Create Your Account"
            )
        },
        mainContent = {
            RegisterUserScreen(
                state = registerViewModel.state,
                onAction = { action ->
                    when (action) {
                        RegisterAction.OnLoginClick -> onLoginClick()
                        else -> Unit
                    }
                    registerViewModel.registerActions(action)
                }
            )
        }
    )
}

@Composable
fun RegisterUserScreen(
    modifier: Modifier = Modifier,
    state: RegisterUserState,
    onAction: (RegisterAction) -> Unit
) {
    val autofillManager = LocalAutofillManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseInputField(
            state = state.fullName,
            isError = state.nameValidationState.isValid,
            hintText = "Name",
            textFieldIcon = {
                if (state.nameValidationState.isValid) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "valid input",
                        tint = taskyColors.validInput,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            },
        )
        BaseInputField(
            state = state.email,
            isError = state.isEmailValid,
            hintText = "Email",
            textFieldIcon = {
                if (state.isEmailValid) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "valid input",
                        tint = taskyColors.validInput,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            },
        )
        PasswordTextField(
            state = state.password,
            isPasswordValid = state.passwordValidationState.isValidPassword,
        )
        AuthCtaButton(
            modifier = Modifier
                .padding(top = 16.dp),
            buttonText = "GET STARTED",
            isButtonEnabled = state.canRegister && !state.isRegistering,
            isLoading = state.isRegistering,
            onButtonClick = {
                onAction(RegisterAction.OnRegisterClick)
                autofillManager?.commit()
            }
        )
        AuthScreenFooter(
            navigateToScreen = { onAction(RegisterAction.OnLoginClick) },
            accountRegisteredPrompt = "Already have an account?",
            loginOrSignupPrompt = "LOG IN"
        )
    }
}
