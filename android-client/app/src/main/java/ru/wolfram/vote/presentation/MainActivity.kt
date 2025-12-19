package ru.wolfram.vote.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.rememberNavController
import ru.wolfram.vote.App
import ru.wolfram.vote.R
import ru.wolfram.vote.di.AppComponent
import ru.wolfram.vote.presentation.theme.AppTheme

val LocalAppComponent = staticCompositionLocalOf<AppComponent> {
    error("AppComponent not provided!")
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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