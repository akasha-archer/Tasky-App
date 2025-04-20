package com.example.taskyapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.auth.presentation.AuthorizationCtaButton
import com.example.taskyapplication.auth.presentation.PasswordTextField
import com.example.taskyapplication.auth.presentation.UserInfoTextField

@Composable
fun AccountCreationScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
// Handle user input change: NewUserRegistration object
        UserInfoTextField(
            userInput = "",
            onUserInputChange = {
                // Update name field to send request
            },
            placeholderText = "Name",
            keyboardType = KeyboardType.Email
        )
        UserInfoTextField(
            userInput = "",
            onUserInputChange = {
                // Update email field to send request
            },
            placeholderText = "Enter your email",
            keyboardType = KeyboardType.Email
        )
        PasswordTextField(
            userInput = "",
            onUserInputChange = {
                // Update password field to send request
            },
        )

        AuthorizationCtaButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Register",
            onButtonClick = onRegisterClick
            // register endpoint
        )

    }
}


@Composable
fun RegisteredUserScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // UserLoginData Object
        UserInfoTextField(
            userInput = "",
            onUserInputChange = {
                // Update email field to send login request
            },
            placeholderText = "Enter your email",
            keyboardType = KeyboardType.Email
        )
        PasswordTextField(
            userInput = "",
            onUserInputChange = {
                // Update password field to send login request
            },
        )

        AuthorizationCtaButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "LOG IN",
            onButtonClick = onRegisterClick
            // call login endpoint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    AccountCreationScreen()
}

@Preview(showBackground = true)
@Composable
fun RegisteredUserScreenPreview() {
    RegisteredUserScreen()
}