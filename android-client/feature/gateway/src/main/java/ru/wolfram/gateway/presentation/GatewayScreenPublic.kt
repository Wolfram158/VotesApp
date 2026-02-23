package ru.wolfram.gateway.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ru.wolfram.common.presentation.LocalAppComponent
import ru.wolfram.common.presentation.RefreshWithEmailCode
import ru.wolfram.common.presentation.RegistrationForEmailCode
import ru.wolfram.common.presentation.Votes
import ru.wolfram.common.presentation.logCreation
import ru.wolfram.gateway.di.DaggerGatewayComponent

@Composable
fun AnimatedContentScope.GatewayScreenPublic(
    navHostController: NavHostController,
    entry: NavBackStackEntry
) {
    val appComponent = LocalAppComponent.current
    val gatewayComponent =
        remember(entry) {
            DaggerGatewayComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .also {
                    logCreation(it::class)
                }
        }
    val factory = remember(gatewayComponent) {
        gatewayComponent.getGatewayViewModelFactory()
    }
    val viewModel = viewModel<GatewayViewModel>(factory = factory)
    GatewayScreen(
        viewModel,
        {
            navHostController.navigate(Votes)
        }, { username ->
            navHostController.navigate(RefreshWithEmailCode(username))
        }) {
        navHostController.navigate(RegistrationForEmailCode) {
            restoreState = true
        }
    }
}