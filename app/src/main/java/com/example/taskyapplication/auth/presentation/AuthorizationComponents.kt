package com.example.taskyapplication.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

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

@Composable
fun PasswordTextField(
    userInput: String,
    onUserInputChange: (String) -> Unit,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
        UserInfoTextField(
            userInput = userInput,
            onUserInputChange = onUserInputChange,
            keyboardType = KeyboardType.Password,
            placeholderText = stringResource(R.string.register_login_password_placeholder),
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            textFieldIcon = {
                ShowOrHidePassword(
                    passwordVisible = passwordVisible,
                    onIconClick = { passwordVisible = !passwordVisible }
                )
            }
        )
}

@Composable
fun UserInfoTextField(
    modifier: Modifier = Modifier,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    placeholderText: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    textFieldIcon: @Composable () -> Unit = { },
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(vertical = 16.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = taskyColors.inputFieldGray),
        value = userInput,
        onValueChange = onUserInputChange,
        singleLine = true,
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.bodyMedium,
        trailingIcon = textFieldIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = taskyColors.inputHintGray,
            unfocusedTextColor = taskyColors.inputHintGray,
            disabledTextColor = taskyColors.inputText,
            errorTextColor = taskyColors.error,
            unfocusedBorderColor = taskyColors.inputFieldGray,
            focusedBorderColor = taskyColors.inputFieldGray,

            ),
        label = {
            Text(
                text = placeholderText,
                color = taskyColors.onSurfaceVariant,
                style = TaskyTypography.bodyMedium,
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
fun AuthorizationCtaButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onButtonClick: () -> Unit = { },
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(25.dp)),
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = taskyColors.primary,
            contentColor = taskyColors.onPrimary,
//            disabledContainerColor = taskyColors.secondaryBackground,
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
        userInput = "FirstName LastName",
        onUserInputChange = { },
        placeholderText = "NAME"
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun PasswordInputPreview() {
    PasswordTextField(
        userInput = "1234abc",
        onUserInputChange = { },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun CtaButtonPreview() {
    AuthorizationCtaButton(
        buttonText = "Sign In"
    )
}