package com.example.newsapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimens(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 12.dp,
    val paddingExtraLarge: Dp = 16.dp,
    val cardElevation: Dp = 2.dp,
)

val LocalDimens = staticCompositionLocalOf { AppDimens() }
