package ru.wolfram.refresh_with_email_code.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ru.wolfram.common.presentation.LocalAppComponent
import ru.wolfram.common.presentation.RefreshWithEmailCode
import ru.wolfram.common.presentation.logCreation
import ru.wolfram.refresh_with_email_code.di.DaggerRefreshWithEmailCodeComponent

@Composable
fun AnimatedContentScope.RefreshWithEmailCodeScreenPublic(
    navHostController: NavHostController,
    entry: NavBackStackEntry
) {
    val route = entry.toRoute<RefreshWithEmailCode>()
    val appComponent = LocalAppComponent.current
    val refreshWithEmailCodeComponent =
        remember(entry) {
            DaggerRefreshWithEmailCodeComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .also {
                    logCreation(it::class)
                }
        }
    val factory = remember(refreshWithEmailCodeComponent) {
        refreshWithEmailCodeComponent.getRefreshWithEmailCodeViewModelFactory()
    }
    val viewModel = viewModel<RefreshWithEmailCodeViewModel>(factory = factory)
    RefreshWithEmailCodeScreen(
        viewModel,
        route.username
    ) {
        navHostController.popBackStack()
    }
}