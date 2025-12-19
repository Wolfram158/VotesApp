package ru.wolfram.vote.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.sp

val LocalAppTheme = compositionLocalOf<AppTheme> {
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