package com.example.taskyapplication.auth.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskyapplication.auth.domain.RegisterUserState
import com.example.taskyapplication.auth.domain.UserLoginData

@Composable
fun AccountCreationScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: (RegisterUserState) -> Unit = {}
) {
//    var registrationName by rememberSaveable {
//        mutableStateOf("fullName")
//    }
//
//    var registrationEmail by rememberSaveable {
//        mutableStateOf("email")
//    }
//
//    var registrationPassword by rememberSaveable {
//        mutableStateOf("password")
//    }
//
//    val newUserData by rememberSaveable {
//        mutableStateOf(
//            RegisterUserState(
//                fullName = registrationName,
//                email = registrationEmail,
//                password = registrationPassword
//            )
//        )
//    }
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        UserInfoTextField(
//            userInput = registrationName,
//            onUserInputChange = {
//                registrationName = it
//            },
//            placeholderText = "Name",
//            keyboardType = KeyboardType.Email
//        )
//        UserInfoTextField(
//            userInput = registrationEmail,
//            onUserInputChange = {
//                registrationEmail = it
//            },
//            placeholderText = "Enter your email",
//            keyboardType = KeyboardType.Email
//        )
//        PasswordTextField(
//            userInput = registrationPassword,
//            onUserInputChange = {
//                registrationPassword = it
//            },
//        )

//        AuthorizationCtaButton(
//            modifier = Modifier.padding(top = 16.dp),
//            buttonText = "Register",
//            onButtonClick = {
//                onRegisterClick(newUserData)
//                // navigate to log in screen
//            }
//        )
//    }
}


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: (UserLoginData) -> Unit = {},
) {
//    var emailInput by rememberSaveable {
//        mutableStateOf("email")
//    }
//
//    var passwordInput by rememberSaveable {
//        mutableStateOf("password")
//    }
//
//    val userLoginData by rememberSaveable {
//        mutableStateOf(
//            UserLoginData(
//                email = emailInput,
//                password = passwordInput
//            )
//        )
//    }
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        UserInfoTextField(
//            userInput = emailInput,
//            onUserInputChange = {
//                emailInput = it
//            },
//            placeholderText = "Enter your email",
//            keyboardType = KeyboardType.Email
//        )
//        PasswordTextField(
//            userInput = passwordInput,
//            onUserInputChange = {
//                passwordInput = it
//            },
//        )
//
//        AuthorizationCtaButton(
//            modifier = Modifier.padding(top = 16.dp),
//            buttonText = "LOG IN",
//            onButtonClick = {
//                onLoginClick(userLoginData)
//                // navigate to agenda
//            }
//        )
//    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    AccountCreationScreen(
        onRegisterClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun RegisteredUserScreenPreview() {
    LoginScreen(
        onLoginClick = {}
    )
}