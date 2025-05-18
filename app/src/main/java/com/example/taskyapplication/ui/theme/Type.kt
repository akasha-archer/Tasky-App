package com.example.taskyapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.taskyapplication.R

// custom fonts
val interFamily400 = FontFamily(
    Font(R.font.inter_regular, FontWeight.W400)
)
val interFamily500 = FontFamily(
    Font(R.font.inter_medium, FontWeight.W500)
)
val interFamily600 = FontFamily(
    Font(R.font.inter_semibold, FontWeight.W600)
)
val interFamily700 = FontFamily(
    Font(R.font.inter_bold, FontWeight.W700),
)

// Set of Material typography styles to start with
val TaskyTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = interFamily400,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = interFamily600,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.0.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = interFamily600,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = interFamily700,
        fontSize = 28.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = interFamily700,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = interFamily600,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = interFamily400,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
)