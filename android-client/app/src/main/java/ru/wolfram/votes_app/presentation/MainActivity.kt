package ru.wolfram.votes_app.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.rememberNavController
import ru.wolfram.common.di.AppComponent
import ru.wolfram.votes_app.App
import ru.wolfram.votes_app.presentation.theme.AppTheme

val LocalAppComponent = staticCompositionLocalOf<AppComponent> {
    error("AppComponent not provided!")
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appComponent = (application as App).appComponent

        setContent {
            AppTheme {
                CompositionLocalProvider(LocalAppComponent provides appComponent) {
                    val navHostController = rememberNavController()
                    NavGraph(navHostController)
                }
            }
        }
    }
}