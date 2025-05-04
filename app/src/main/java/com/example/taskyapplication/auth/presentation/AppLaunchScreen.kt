package com.example.taskyapplication.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.auth.presentation.utils.AuthScreenTitle

@Composable
fun UserRegistrationScreen(
    modifier: Modifier = Modifier,
) {
    TaskyBaseScreen(
        screenHeader = {
            AuthScreenTitle(
                titleText = "Create Your Account"
            )
        },
        mainContent = {
            AccountCreationContent(
                modifier = modifier,
            )
        }
    )
}

@Composable
fun UserLoginScreen(
    modifier: Modifier = Modifier,
) {
    TaskyBaseScreen(
        screenHeader = {
            AuthScreenTitle(
                titleText = "Welcome Back!"
            )
        },
        mainContent = {
            LoginContent(
                modifier = modifier,
            )
        }
    )
}