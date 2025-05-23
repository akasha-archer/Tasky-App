package com.example.taskyapplication.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun BaseInputField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    isValid: Boolean = false,
    hintText: String = "",
    textFieldIcon: @Composable (() -> Unit)?,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
    ) {
        BasicTextField(
            state = state,
            modifier = modifier
                .fillMaxWidth()
                .height(84.dp)
                .padding(top = 24.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .onFocusChanged { isFocused = it.isFocused }
                .background(color = taskyColors.inputFieldGray)
                .border(
                    width = 1.dp,
                    color = when {
                        !isValid && !isFocused && state.text.isNotEmpty() -> taskyColors.error
                        isFocused -> taskyColors.textFieldFocusBorder
                        else -> Color.Transparent
                    }
                )
                .semantics {
                    contentType = androidx.compose.ui.autofill.ContentType.PersonFullName +
                            androidx.compose.ui.autofill.ContentType.EmailAddress
                },
            textStyle = TaskyTypography.bodyMedium.copy(
                color = taskyColors.inputText
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                 imeAction = imeAction
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            cursorBrush = SolidColor(taskyColors.inputText),
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hintText,
                                color = taskyColors.inputHintGray,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerTextField()
                    }
                    if (textFieldIcon != null) {
                        textFieldIcon()
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun AuthCtaButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    isButtonEnabled: Boolean,
    isLoading: Boolean,
    onButtonClick: () -> Unit,
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
            disabledContainerColor = taskyColors.onSurfaceVariant.copy(
                alpha = 0.7f,
            )
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(36.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = taskyColors.onPrimary
            )
            Text(
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                text = buttonText.uppercase(),
                color = taskyColors.onPrimary,
                style = TaskyTypography.labelMedium
            )
        }
    }
}

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
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        BasicText(
            text = buildAnnotatedString {
                append("${accountRegisteredPrompt}  ".uppercase())
                withLink(
                    LinkAnnotation.Clickable(
                        tag = "",
                        styles = TextLinkStyles(
                            style = TaskyTypography.labelSmall.toSpanStyle().copy(
                                color = taskyColors.link
                            )
                        ),
                        linkInteractionListener = { navigateToScreen() }
                    ),
                ) {
                    append(loginOrSignupPrompt.uppercase())
                }
            },
            style = TaskyTypography.labelSmall.copy(
                color = taskyColors.onSurfaceVariant,
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun PlaygroundPreview() {
    BaseInputField(
        state = remember { TextFieldState() },
        hintText = "Name",
        isValid = false,
        textFieldIcon = {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "valid input",
                tint = taskyColors.validInput,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun CtaButtonPreview() {
    AuthCtaButton(
        buttonText = "Sign In",
        isLoading = false,
        isButtonEnabled = true,
        onButtonClick = {},
    )
}
