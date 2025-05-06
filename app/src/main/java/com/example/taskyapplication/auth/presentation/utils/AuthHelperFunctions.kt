package com.example.taskyapplication.auth.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.taskyapplication.R
import com.example.taskyapplication.ui.theme.TaskyDesignSystem.Companion.taskyColors
import com.example.taskyapplication.ui.theme.TaskyTypography

fun TextFieldState.textAsFlow() = snapshotFlow { text }

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
fun ShowInputValidationIcon(
    modifier: Modifier = Modifier,
    isError: Boolean,
) {
    val iconAlpha = if (isError) 0f else 1f
    Icon(
        modifier = modifier.alpha(iconAlpha),
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