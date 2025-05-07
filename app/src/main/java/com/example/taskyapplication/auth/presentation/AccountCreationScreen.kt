package com.example.taskyapplication.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
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
import com.example.taskyapplication.auth.domain.RegisterUserState
import com.example.taskyapplication.auth.presentation.components.AuthScreenFooter
import com.example.taskyapplication.auth.presentation.components.AuthorizationCtaButton
import com.example.taskyapplication.auth.presentation.components.BaseInputField
import com.example.taskyapplication.auth.presentation.components.PasswordTextField
import com.example.taskyapplication.auth.presentation.components.UserInfoTextField
import com.example.taskyapplication.auth.presentation.utils.AuthScreenTitle
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun RegisterRoot(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    TaskyBaseScreen(
        screenHeader = {
            AuthScreenTitle(
                modifier = modifier.padding(top = 24.dp),
                titleText = "Create Your Account"
            )
        },
        mainContent = {
            RegisterUserScreen(
                state = authViewModel.state,
            )
        }
    )
}

@Composable
fun RegisterUserScreen(
    modifier: Modifier = Modifier,
    state: RegisterUserState,
    onRegister: () -> Unit = {}
) {
    val autofillManager = LocalAutofillManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // user name input
        BaseInputField(
            state = state.fullName,
            isError = !state.nameValidationState.isValid,
            supportingText = "Please enter a valid name",
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
        // email input
        BaseInputField(
            state = state.email,
            isError = !state.isEmailValid,
            supportingText = "Please enter a valid email",
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
        AuthorizationCtaButton(
            modifier = Modifier
                .padding(top = 16.dp),
            buttonText = "GET STARTED",
            isButtonEnabled = true,
            onButtonClick = {
                autofillManager?.commit()
                // call register endpoint

                // if registration is successful (check state)
                // navigate to log in screen
            }
        )
        AuthScreenFooter(
            navigateToScreen = { },
            accountRegisteredPrompt = "Already have an account?",
            loginOrSignupPrompt = "LOG IN"
        )
    }
}
