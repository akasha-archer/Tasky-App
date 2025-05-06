package com.example.taskyapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun BaseInputField2(
    modifier: Modifier = Modifier,
    userInput: TextFieldState,
    supportingText: String = "",
    errorMessage: @Composable (() -> Unit),
    isError: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
//        Box(
//            modifier = modifier
//                .fillMaxWidth()
//                .align(Alignment.End)
//        ) {
            BasicTextField(
                state = userInput,
                modifier = modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 12.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = taskyColors.inputFieldGray),
                textStyle = TaskyTypography.bodyMedium,
                keyboardOptions = keyboardOptions,
                onKeyboardAction = KeyboardActionHandler { },
                lineLimits = TextFieldLineLimits.SingleLine,
                cursorBrush = SolidColor(taskyColors.inputText),
                decorator = {
                    Icon(
                        modifier = modifier
                            .size(4.dp)
                            .align(Alignment.End),
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Your input is valid",
                        tint = taskyColors.validInput,
                    )
                }
            )
        }
        Spacer(modifier = Modifier.padding(top = 4.dp))
        Text(
            text = supportingText,
            style = TaskyTypography.bodySmall,
            color = taskyColors.onSurfaceVariant
        )
//    }
}

val textFieldStateSample = TextFieldState(
    initialText = "First Name",
)

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun PlaygroundPreview() {
    BaseInputField2(
        userInput = textFieldStateSample,
        isError = false,
        errorMessage = {
            Text(
                text = "An input error has occurred",
                color = taskyColors.error,
                style = TaskyTypography.bodySmall
            )
        },
    )
}
