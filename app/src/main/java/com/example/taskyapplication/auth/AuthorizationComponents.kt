package com.example.taskyapplication.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.SurfaceHigher

@Composable
fun PasswordTextField(
    userInput: String,
    onUserInputChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        UserInfoTextField(
            userInput = userInput,
            onUserInputChange = onUserInputChange,
            keyboardType = KeyboardType.Password,
            placeholderText = stringResource(R.string.account_password_placeholder)
        )
        Icon(
            modifier = modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd)
                .clickable {  },
            painter = painterResource(R.drawable.eye_closed),
            tint = Color.Green,
            contentDescription = "hide password"
        )
    }
}

@Composable
fun UserInfoTextField(
    modifier: Modifier = Modifier,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    placeholderText: String,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .width(328.dp)
            .height(56.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = SurfaceHigher),
        value = userInput,
        onValueChange = onUserInputChange,
        singleLine = true,
        placeholder = {
            Text(text = placeholderText)
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
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .width(328.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(25.dp)
            ),
        onClick = { },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = stringResource(R.string.register_button_cta))
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
    AuthorizationCtaButton()
}