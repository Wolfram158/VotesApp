package ru.wolfram.registration_for_email_code.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ru.wolfram.common.presentation.LocalAppComponent
import ru.wolfram.common.presentation.RegistrationWithEmailCode
import ru.wolfram.common.presentation.logCreation
import ru.wolfram.registration_for_email_code.di.DaggerRegistrationForEmailCodeComponent

@Composable
fun AnimatedContentScope.RegistrationForEmailCodeScreenPublic(
    navHostController: NavHostController,
    entry: NavBackStackEntry
) {
    val appComponent = LocalAppComponent.current
    val registrationForEmailCodeComponent =
        remember(entry) {
            DaggerRegistrationForEmailCodeComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .also {
                    logCreation(it::class)
                }
        }
    val factory = remember(registrationForEmailCodeComponent) {
        registrationForEmailCodeComponent.getRegistrationForEmailCodeViewModelFactory()
    }
    val viewModel = viewModel<RegistrationForEmailCodeViewModel>(factory = factory)
    RegistrationForEmailCodeScreen(
        viewModel
    ) { username, email, password ->
        navHostController.navigate(RegistrationWithEmailCode(username, email, password)) {
            restoreState = true
        }
    }
}