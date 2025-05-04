package com.example.taskyapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun BaseInputField(
    modifier: Modifier = Modifier,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    placeholderText: String = "",
    errorMessage: @Composable (() -> Unit),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textFieldIcon: @Composable () -> Unit,
    isError: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    Column {
        TextField(
            value = userInput,
            onValueChange = onUserInputChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = taskyColors.inputFieldGray),
            singleLine = true,
            visualTransformation = visualTransformation,
            textStyle = TaskyTypography.bodyMedium,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = TaskyTypography.bodyMedium,
                    color = taskyColors.onSurfaceVariant
                )
            },
            supportingText = { errorMessage() },
            trailingIcon = { textFieldIcon() },
            isError = isError,
            colors = TextFieldDefaults.colors(
                focusedTextColor = taskyColors.inputHintGray,
                unfocusedTextColor = taskyColors.inputHintGray,
                disabledTextColor = taskyColors.inputText,
                cursorColor = taskyColors.inputText,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorTextColor = taskyColors.error,
                errorCursorColor = taskyColors.error,
                errorIndicatorColor = taskyColors.error,
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun PlaygroundPreview() {
    BaseInputField(
        userInput = "FirstName LastName",
        onUserInputChange = { },
        isError = false,
        errorMessage = {
            Text(
                text = "An input error has occurred",
                color = taskyColors.error,
                style = TaskyTypography.bodySmall
            )
        },
        textFieldIcon = { },
    )
}