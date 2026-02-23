package ru.wolfram.create_vote.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ru.wolfram.common.presentation.LocalAppComponent
import ru.wolfram.common.presentation.logCreation
import ru.wolfram.create_vote.di.DaggerCreateVoteComponent

@Composable
fun AnimatedContentScope.CreateVoteScreenPublic(
    navHostController: NavHostController,
    entry: NavBackStackEntry
) {
    val appComponent = LocalAppComponent.current
    val createVoteComponent =
        remember(entry) {
            DaggerCreateVoteComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .also {
                    logCreation(it::class)
                }
        }
    val factory = remember(createVoteComponent) {
        createVoteComponent.getCreateVoteViewModelFactory()
    }
    val viewModel = viewModel<CreateVoteViewModel>(factory = factory)
    CreateVoteScreen(
        viewModel
    ) {
        navHostController.popBackStack()
    }
}