package com.example.taskyapplication.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBackground_Dark,
    onPrimary = TextOnPrimary_Dark,
    surface = SecondaryBackground_Dark,
    onSurface = InputText_Dark,
    onSurfaceVariant = InputHintGray_Dark,
    surfaceContainerHigh = InputFieldGray_Dark,
    surfaceBright = Link_Dark,
    error = Error_Dark,
    outline = Outline_Dark,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBackground,
    onPrimary = TextOnPrimary,
    surface = SecondaryBackground,
    surfaceContainerHigh = InputFieldGray,
    onSurface = InputText,
    onSurfaceVariant = InputHintGray,
    surfaceBright = Link_Light,
    error = Error_Light,
    outline = Outline_Light
)

@Composable
fun TaskyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}