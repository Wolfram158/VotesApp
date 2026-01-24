package ru.wolfram.common.presentation

import androidx.compose.runtime.staticCompositionLocalOf
import ru.wolfram.common.di.AppComponent

val LocalAppComponent = staticCompositionLocalOf<AppComponent> {
    error("AppComponent not provided!")
}