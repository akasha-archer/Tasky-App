package com.example.taskyapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taskyapplication.auth.domain.NewUserRegistrationData
import com.example.taskyapplication.auth.domain.UserLoginData
import com.example.taskyapplication.auth.presentation.AuthorizationCtaButton
import com.example.taskyapplication.auth.presentation.PasswordTextField
import com.example.taskyapplication.auth.presentation.UserInfoTextField

@Composable
fun AccountCreationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onRegisterClick: (NewUserRegistrationData) -> Unit = {}
) {
    var registrationName by rememberSaveable {
        mutableStateOf("fullName")
    }

    var registrationEmail by rememberSaveable {
        mutableStateOf("email")
    }

    var registrationPassword by rememberSaveable {
        mutableStateOf("password")
    }

    val newUserData by rememberSaveable {
        mutableStateOf(
            NewUserRegistrationData(
                fullName = registrationName,
                email = registrationEmail,
                password = registrationPassword
            )
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfoTextField(
            userInput = registrationName,
            onUserInputChange = {
                registrationName = it
            },
            placeholderText = "Name",
            keyboardType = KeyboardType.Email
        )
        UserInfoTextField(
            userInput = registrationEmail,
            onUserInputChange = {
                registrationEmail = it
            },
            placeholderText = "Enter your email",
            keyboardType = KeyboardType.Email
        )
        PasswordTextField(
            userInput = registrationPassword,
            onUserInputChange = {
                registrationPassword = it
            },
        )

        AuthorizationCtaButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "Register",
            onButtonClick = {
                onRegisterClick(newUserData)
                // navigate to log in screen
            }
        )
    }
}


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    onLoginClick: (UserLoginData) -> Unit = {},
) {
    var emailInput by rememberSaveable {
        mutableStateOf("email")
    }

    var passwordInput by rememberSaveable {
        mutableStateOf("password")
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfoTextField(
            userInput = emailInput,
            onUserInputChange = {
                emailInput = it
            },
            placeholderText = "Enter your email",
            keyboardType = KeyboardType.Email
        )
        PasswordTextField(
            userInput = passwordInput,
            onUserInputChange = {
                passwordInput = it
            },
        )

        AuthorizationCtaButton(
            modifier = Modifier.padding(top = 16.dp),
            buttonText = "LOG IN",
            onButtonClick = {
                onLoginClick(userLoginData)
                // navigate to agenda
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    AccountCreationScreen(
        navController = rememberNavController(),
        onRegisterClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun RegisteredUserScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        onLoginClick = {}
    )
}