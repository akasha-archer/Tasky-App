package com.example.taskyapplication.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.InputFieldGray
import com.example.taskyapplication.ui.theme.InputText
import com.example.taskyapplication.ui.theme.TaskyTypography

@Composable
fun PasswordTextField(
    userInput: String,
    onUserInputChange: (String) -> Unit,
) {
    Box {
        UserInfoTextField(
            userInput = userInput,
            onUserInputChange = onUserInputChange,
            keyboardType = KeyboardType.Password,
            placeholderText = stringResource(R.string.account_password_placeholder)
        )
//        Image(
//            modifier = Modifier
//                .padding(end = 16.dp)
//                .align(Alignment.CenterEnd)
//                .clickable { TODO("Not yet implemented") },
//            painter = painterResource(R.drawable.eye_closed),
//            contentDescription = "hide password"
//        )
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
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = InputFieldGray),
        value = userInput,
        onValueChange = onUserInputChange,
        singleLine = true,
        textStyle = TaskyTypography.BODY_MEDIUM.value,
        colors = TextFieldDefaults.colors(
            focusedTextColor = InputText,
            unfocusedTextColor = InputText,
            disabledTextColor = InputText,
            errorTextColor = MaterialTheme.colorScheme.error,
        ),
        placeholder = {
            Text(
                text = placeholderText,
                color = InputText,
                style = TaskyTypography.BODY_MEDIUM.value,
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
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = buttonText.uppercase(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = TaskyTypography.LABEL_MEDIUM.value
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