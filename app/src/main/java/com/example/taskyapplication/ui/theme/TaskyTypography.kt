package com.example.taskyapplication.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

enum class TaskyTypography(
    val value: TextStyle
) {
    BODY_MEDIUM(
        value = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 1.0.sp
        )
    ),
   LABEL_MEDIUM(
        value = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 1.0.sp,
        )
    ),
    LABEL_SMALL(
        value = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.0.sp
        )
    ),
    LABEL_SMALL_LINK(
        value = TextStyle(
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.0.sp
        )
    ),
    HEADLINE_LARGE(
        value = TextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 28.sp,
            lineHeight = 30.sp,
            letterSpacing = 0.sp
        )
    ),
}
