package com.example.taskyapplication.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.taskyapplication.R

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
    Font(R.font.inter_medium, FontWeight.W500),
)

enum class TaskyTypography(
    val value: TextStyle
) {
    BODY_MEDIUM(
        value = TextStyle(
            fontFamily = interFamily400,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.1.sp
        )
    ),
   LABEL_MEDIUM(
        value = TextStyle(
            fontFamily = interFamily600,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.1.sp,
        )
    ),
    LABEL_SMALL(
        value = TextStyle(
            fontFamily = interFamily600,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        )
    ),
    LABEL_SMALL_LINK(
        value = TextStyle(
            fontFamily = interFamily600,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.0.sp
        )
    ),
    HEADLINE_LARGE(
        value = TextStyle(
            fontFamily = interFamily700,
            fontSize = 28.sp,
            lineHeight = 30.sp,
            letterSpacing = 0.0.sp
        )
    ),
}
