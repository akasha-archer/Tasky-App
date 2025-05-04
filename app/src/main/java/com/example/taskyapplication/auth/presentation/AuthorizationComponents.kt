package com.example.taskyapplication.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.BaseInputField
import com.example.taskyapplication.auth.presentation.utils.ShowInputValidationIcon
import com.example.taskyapplication.auth.presentation.utils.ShowOrHidePassword
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun AuthScreenFooter(
    modifier: Modifier = Modifier,
    navigateToScreen: () -> Unit,
    accountRegisteredPrompt: String,
    loginOrSignupPrompt: String
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToScreen()
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = accountRegisteredPrompt.uppercase(),
            style = TaskyTypography.labelSmall,
            color = taskyColors.onSurfaceVariant,
        )
        Text(
            text = loginOrSignupPrompt.uppercase(),
            style = TaskyTypography.labelSmall,
            color = taskyColors.link,
        )
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    placeholderText: String,
    isError: Boolean,
    errorMessage: @Composable (() -> Unit),
    keyboardType: KeyboardType = KeyboardType.Password,
    onValidatePassword: (String) -> Unit = {}
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    BaseInputField(
        modifier = modifier,
        userInput = userInput,
        onUserInputChange = onUserInputChange,
        placeholderText = placeholderText,
        isError = isError,
        errorMessage = { errorMessage() },
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else PasswordVisualTransformation(),
        textFieldIcon = {
            ShowOrHidePassword(
                passwordVisible = passwordVisible,
                onIconClick = { passwordVisible = !passwordVisible }
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onValidatePassword(userInput)
            },
        )
    )
}

@Composable
fun UserInfoTextField(
    modifier: Modifier = Modifier,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    isError: Boolean,
    placeholderText: String,
    errorMessage: @Composable (() -> Unit),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValidateInput: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    BaseInputField(
        modifier = modifier,
        userInput = userInput,
        onUserInputChange = onUserInputChange,
        placeholderText = placeholderText,
        isError = isError,
        errorMessage = { errorMessage() },
        visualTransformation = visualTransformation,
        textFieldIcon = {
            ShowInputValidationIcon(
                isError = (isError || userInput.isEmpty())
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onValidateInput(userInput)
                focusManager.moveFocus(FocusDirection.Down)
            },
        )
    )
}

@Composable
fun AuthorizationCtaButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    isButtonEnabled: Boolean = false,
    onButtonClick: () -> Unit = { },
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(25.dp)),
        onClick = onButtonClick,
        enabled = isButtonEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = taskyColors.primary,
            contentColor = taskyColors.onPrimary,
            disabledContainerColor = taskyColors.primary,
//            disabledContentColor = taskyColors.secondaryBackground
        )
    ) {
        Text(
            text = buttonText.uppercase(),
            color = taskyColors.onPrimary,
            style = TaskyTypography.labelMedium
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun UserInputPreview() {
    UserInfoTextField(
        userInput = "",
        onUserInputChange = { },
        placeholderText = "Enter your name",
        isError = false,
        errorMessage = {
            Text(
                text = "An input error has occurred",
                color = taskyColors.error,
                style = TaskyTypography.bodySmall
            )
        },
        keyboardType = KeyboardType.Text,
        onValidateInput = { },
//        textFieldIcon = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun PasswordInputPreview() {
    PasswordTextField(
        userInput = "1234abc",
        onUserInputChange = { },
        placeholderText = "Enter your password",
        isError = false,
        errorMessage = {
            Text(
                text = "An input error has occurred",
                color = taskyColors.error,
                style = TaskyTypography.bodySmall
            )
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun CtaButtonPreview() {
    AuthorizationCtaButton(
        buttonText = "Sign In"
    )
}