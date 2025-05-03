package com.example.taskyapplication.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.TaskyBaseScreen
import com.example.taskyapplication.auth.domain.NewUserRegistrationData
import com.example.taskyapplication.auth.domain.UserLoginData
import com.example.taskyapplication.ui.theme.TaskyDesignSystem
import com.example.taskyapplication.ui.theme.TaskyTypography

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

@Composable
fun AuthScreenTitle(
    modifier: Modifier = Modifier,
    titleText: String = "Screen Title"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(top = 64.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color.Black),
            textAlign = TextAlign.Center,
            text = titleText,
            color = Color.White,
            style = TaskyTypography.headlineLarge,
        )
    }
}


@Composable
fun AccountCreationContent(
    modifier: Modifier = Modifier,
    onRegisterClick: (NewUserRegistrationData) -> Unit = {}
) {
    var registrationName by rememberSaveable {
        mutableStateOf("")
    }

    var registrationEmail by rememberSaveable {
        mutableStateOf("")
    }

    var registrationPassword by rememberSaveable {
        mutableStateOf("")
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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfoTextField(
            userInput = registrationName,
            onUserInputChange = {
                registrationName = it
            },
            placeholderText = stringResource(R.string.register_name_placeholder),
            keyboardType = KeyboardType.Email
        )
        UserInfoTextField(
            userInput = registrationEmail,
            onUserInputChange = {
                registrationEmail = it
            },
            placeholderText = stringResource(R.string.register_email_placeholder),
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
            buttonText = "GET STARTED",
            onButtonClick = {
                onRegisterClick(newUserData)
                // navigate to log in screen
            }
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                // navigate to log in screen
            },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account?  ".uppercase(),
                style = TaskyTypography.labelSmall,
                color = TaskyDesignSystem.taskyColors.onSurfaceVariant
            )
            Text(
                text = "LOG IN",
                style = TaskyTypography.labelSmall,
                color = TaskyDesignSystem.taskyColors.link,
            )
        }
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
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // navigate to registration screen
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account?  ".uppercase(),
                style = TaskyTypography.labelSmall,
                color = TaskyDesignSystem.taskyColors.onSurfaceVariant,
            )
            Text(
                text = "Sign up".uppercase(),
                style = TaskyTypography.labelSmall,
                color = TaskyDesignSystem.taskyColors.link,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    AccountCreationContent(
        onRegisterClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun RegisteredUserScreenPreview() {
    LoginContent(
        onLoginClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AuthTitlePreview() {
    AuthScreenTitle(
        titleText = "Create Account"
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    UserRegistrationScreen()
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    UserLoginScreen()
}