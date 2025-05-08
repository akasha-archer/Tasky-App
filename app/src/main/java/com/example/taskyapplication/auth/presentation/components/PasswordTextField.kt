package com.example.taskyapplication.auth.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.SecureTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.auth.presentation.utils.ShowOrHidePassword
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    hintText: String = "Password",
    isPasswordValid: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
    ) {
        SecureTextField(
            state = state,
            modifier = modifier
                .fillMaxWidth()
                .height(84.dp)
                .padding(top = 24.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = when {
                        (!isPasswordValid && !isFocused && state.text.isNotEmpty()) -> taskyColors.error
                        isFocused -> taskyColors.textFieldFocusBorder
                        else -> Color.Transparent
                    }
                )
                .onFocusChanged { isFocused = it.isFocused },
            textStyle = TaskyTypography.bodyMedium.copy(
                color = taskyColors.inputText
            ),
            placeholder = {
                if (!isFocused) {
                    Text(
                        text = hintText,
                        style = TaskyTypography.bodyMedium,
                        color = taskyColors.inputHintGray,
                    )
                }

            },
            trailingIcon = {
                ShowOrHidePassword(
                    passwordVisible = isPasswordVisible,
                    onIconClick = { isPasswordVisible = !isPasswordVisible }
                )
            },
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else TextObfuscationMode.Hidden,
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = taskyColors.inputText,
                disabledTextColor = taskyColors.inputHintGray,
                backgroundColor = taskyColors.inputFieldGray,
                cursorColor = taskyColors.inputHintGray,
                errorCursorColor = taskyColors.error,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = taskyColors.error,
                trailingIconColor = taskyColors.inputHintGray.copy(alpha = 0.7f),
                errorTrailingIconColor = taskyColors.error,
                placeholderColor = taskyColors.inputHintGray,
                disabledPlaceholderColor = taskyColors.inputHintGray
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun PasswordTextFieldPreview() {
    PasswordTextField(
        state = remember { TextFieldState() },
        isPasswordValid = false
    )
}