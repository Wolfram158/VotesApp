package ru.wolfram.votes.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import ru.wolfram.common.presentation.CreateVote
import ru.wolfram.common.presentation.LocalAppComponent
import ru.wolfram.common.presentation.Vote
import ru.wolfram.common.presentation.logCreation
import ru.wolfram.votes.di.DaggerVotesComponent

@Composable
fun AnimatedContentScope.VotesScreenPublic(
    navHostController: NavHostController,
    entry: NavBackStackEntry
) {
    val appComponent = LocalAppComponent.current
    val votesComponent =
        remember(entry) {
            DaggerVotesComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .also {
                    logCreation(it::class)
                }
        }
    val factory = remember(votesComponent) {
        votesComponent.getVotesViewModelFactory()
    }
    val viewModel = viewModel<VotesViewModel>(factory = factory)
    VotesScreen(
        viewModel, { title ->
            navHostController.navigate(Vote(title)) {
                restoreState = true
            }
        }) {
        navHostController.navigate(CreateVote) {
            restoreState = true
        }
    }
}