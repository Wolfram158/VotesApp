package ru.wolfram.registration_with_email_code.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ru.wolfram.common.presentation.LocalAppComponent
import ru.wolfram.common.presentation.RegistrationWithEmailCode
import ru.wolfram.common.presentation.logCreation
import ru.wolfram.registration_with_email_code.di.DaggerRegistrationWithEmailCodeComponent

@Composable
fun AnimatedContentScope.RegistrationWithEmailCodeScreenPublic(
    navHostController: NavHostController,
    entry: NavBackStackEntry
) {
    val route = entry.toRoute<RegistrationWithEmailCode>()
    val appComponent = LocalAppComponent.current
    val registrationWithEmailCodeComponent =
        remember(entry) {
            DaggerRegistrationWithEmailCodeComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .also {
                    logCreation(it::class)
                }
        }
    val factory = remember(registrationWithEmailCodeComponent) {
        registrationWithEmailCodeComponent.getRegistrationWithEmailCodeViewModelFactory()
    }
    val viewModel = viewModel<RegistrationWithEmailCodeViewModel>(factory = factory)
    RegistrationWithEmailCodeScreen(
        viewModel,
        route.username,
        route.email,
        route.password
    ) {
        navHostController.popBackStack()
        navHostController.popBackStack()
    }
}