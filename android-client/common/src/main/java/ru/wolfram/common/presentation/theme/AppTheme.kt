package ru.wolfram.vote.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.sp

val LocalAppTheme = staticCompositionLocalOf<AppTheme> {
    error("AppTheme not provided!")
}

object AppTheme {
    val textSize1 = 18.sp
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppTheme provides AppTheme) {
        content()
    }
}