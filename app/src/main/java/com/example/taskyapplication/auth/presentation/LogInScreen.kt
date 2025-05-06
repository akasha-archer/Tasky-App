package com.example.taskyapplication.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskyapplication.auth.domain.UserLoginData

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
//    AccountCreationScreen()
}

@Preview(showBackground = true)
@Composable
fun RegisteredUserScreenPreview() {
    LoginScreen(
        onLoginClick = {}
    )
}