package com.example.taskyapplication.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LocalTaskyColors = compositionLocalOf { TaskyDesignSystem() }

data class TaskyDesignSystem(
    val colors: TaskyColors = TaskyColors()
) {
    companion object {
        val taskyColors
            @Composable
            get() = LocalTaskyColors.current.colors
    }
}

data class TaskyColors(
    val link: Color = Link_Light,
    val primary: Color = PrimaryBackground,
    val onPrimary: Color = TextOnPrimary,
    val surface: Color = SecondaryBackground,
    val surfaceContainerHigh: Color = InputFieldGray,
    val onSurface: Color = InputText,
    val onSurfaceVariant: Color = InputHintGray,
    val surfaceBright: Color = Link_Light,
    val error: Color = Error_Light,
    val outline: Color = Outline_Light,
    val secondaryBackground: Color = SecondaryBackground_Dark,
    val inputText: Color = InputText,
    val inputHintGray: Color = InputHintGray,
    val inputFieldGray: Color = InputFieldGray,
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF6200EE),
    onPrimaryContainer = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3700B3),
    onPrimaryContainer = Color.White,
)

private val DarkTaskyColors = TaskyColors(
    link = Link_Dark,
    primary = PrimaryBackground_Dark,
    onPrimary = TextOnPrimary_Dark,
    surface = SecondaryBackground_Dark,
    onSurface = InputText_Dark,
    onSurfaceVariant = InputHintGray_Dark,
    surfaceContainerHigh = InputFieldGray_Dark,
    surfaceBright = Link_Dark,
    error = Error_Dark,
    outline = Outline_Dark,
    secondaryBackground = SecondaryBackground_Dark,
    inputText = InputText_Dark,
    inputHintGray = InputHintGray_Dark,
    inputFieldGray = InputFieldGray_Dark
)

private val LightTaskyColors = TaskyColors(
    link = Link_Light,
    primary = PrimaryBackground,
    onPrimary = TextOnPrimary,
    surface = SecondaryBackground,
    surfaceContainerHigh = InputFieldGray,
    onSurface = InputText,
    onSurfaceVariant = InputHintGray,
    surfaceBright = Link_Light,
    error = Error_Light,
    outline = Outline_Light,
    secondaryBackground = SecondaryBackground_Dark,
    inputText = InputText,
    inputHintGray = InputHintGray,
    inputFieldGray = InputFieldGray,
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

    val taskyColors = if (darkTheme) DarkTaskyColors else LightTaskyColors

    // Provide both Material3 ColorScheme and custom TaskyColors
    CompositionLocalProvider(
        LocalTaskyColors provides TaskyDesignSystem(
            colors = taskyColors
        )
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = TaskyTypography,
            content = content
        )
    }
}