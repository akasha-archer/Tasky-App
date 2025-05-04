package com.example.taskyapplication.auth.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.auth.domain.EmailValidationState
import com.example.taskyapplication.auth.domain.PasswordValidationState
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

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
fun ValidInputIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier.alpha(1.0f),
        imageVector = Icons.Filled.Done,
        contentDescription = "Your input is valid",
        tint = taskyColors.validInput,
    )
}

// toggle password text visibility
@Composable
fun ShowOrHidePassword(
    modifier: Modifier = Modifier,
    passwordVisible: Boolean,
    onIconClick: () -> Unit
) {
    val visibilityIcon = if (passwordVisible) {
        painterResource(R.drawable.password_visibility_on)
    } else {
        painterResource(R.drawable.password_visibility_off)
    }

    val description = if (passwordVisible) {
        stringResource(R.string.hide_password)
    } else {
        stringResource(R.string.show_password)
    }

    IconButton(
        onClick = onIconClick,
        modifier = modifier
    ) {
        Icon(
            modifier = modifier.alpha(0.7f),
            painter = visibilityIcon,
            contentDescription = description,
            tint = taskyColors.onSurface
        )
    }
}

///**
// * Validates a password based on the following criteria:
// * - Minimum length of 9 characters.
// * - Contains at least one lowercase letter.
// * - Contains at least one uppercase letter.
// * - Contains at least one digit.
// *
// * @param password The password string to validate.
// * @return True if the password meets all criteria, false otherwise.
// */
//fun isPasswordValid(password: String): PasswordValidationState {
//    var errorMessage: String?
//    // Check 1: Minimum length
//    if (password.length < 9) {
//        errorMessage = "Password must be at least 9 characters long."
//        return PasswordValidationState(false, errorMessage)
//    }
//
//    // Check 2: Contains at least one lowercase letter
//    val hasLowercase = password.any { it.isLowerCase() }
//    if (!hasLowercase) {
//        errorMessage = "Password must contain at least one lowercase letter."
//        return PasswordValidationState(false, errorMessage)
//    }
//
//    // Check 3: Contains at least one uppercase letter
//    val hasUppercase = password.any { it.isUpperCase() }
//    if (!hasUppercase) {
//        errorMessage = "Password must contain at least one uppercase letter."
//        return PasswordValidationState(false, errorMessage)
//    }
//
//    // Check 4: Contains at least one digit
//    val hasDigit = password.any { it.isDigit() }
//    if (!hasDigit) {
//        errorMessage = "Password must contain at least one digit."
//        return PasswordValidationState(false, errorMessage)
//    }
//
//    return PasswordValidationState(true, null)
//}
//
///**
// * Validates an email address based on basic criteria:
// * - Must contain the '@' symbol.
// * - Must have characters following the '@' symbol (representing the domain).
// *
// * @param email The email string to validate.
// * @return True if the email meets the basic criteria, false otherwise.
// */
//fun isEmailValid(email: String): EmailValidationState {
//    var errorMessage: String?
//    // Check 1: Must contain '@'
//    val atIndex = email.indexOf('@')
//    if (atIndex == -1) {
//        errorMessage = "Email must contain an '@' symbol."
//        return EmailValidationState(false, errorMessage)
//    }
//
//    // Check 2: Must have something after '@' (domain part)
//    // Ensure '@' is not the last character in the string.
//    if (atIndex == email.length - 1) {
//        errorMessage = "Email must have a domain name after the '@' symbol."
//        return EmailValidationState(false, errorMessage)
//    }
//
//    // Check 3: Ensure there's something before '@' (local part)
//    // Uncomment this block if you want to enforce having a local part.
//    if (atIndex == 0) {
//        errorMessage = "Email must have a local part before the '@' symbol."
//        return EmailValidationState(false, errorMessage)
//    }
//    return EmailValidationState(false, null)
//}

@Preview(showBackground = true)
@Composable
fun AuthTitlePreview() {
    AuthScreenTitle(
        titleText = "Create Account"
    )
}