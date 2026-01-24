package ru.wolfram.votes_app.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ru.wolfram.common.presentation.LocalAppComponent
import ru.wolfram.common.presentation.Vote
import ru.wolfram.common.presentation.logCreation
import ru.wolfram.votes_app.di.DaggerVoteComponent

@Composable
fun AnimatedContentScope.VoteScreenPublic(
    navHostController: NavHostController,
    entry: NavBackStackEntry
) {
    val route = entry.toRoute<Vote>()
    val appComponent = LocalAppComponent.current
    val voteComponent =
        remember(entry) {
            DaggerVoteComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .also {
                    logCreation(it::class)
                }
        }
    val factory = remember(voteComponent) {
        voteComponent.getVoteViewModelFactory()
    }
    val viewModel = viewModel<VoteViewModel>(factory = factory)

    LaunchedEffect(Unit) {
        viewModel.initVoteGetting(route.title)
    }

    VoteScreen(
        viewModel,
        route.title
    )
}