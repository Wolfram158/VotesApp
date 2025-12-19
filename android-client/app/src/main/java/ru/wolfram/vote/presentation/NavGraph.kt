package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.wolfram.vote.di.DaggerCreateVoteComponent
import ru.wolfram.vote.di.DaggerGatewayComponent
import ru.wolfram.vote.di.DaggerVoteComponent
import ru.wolfram.vote.di.DaggerVotesComponent

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Auth
    ) {
        composable<Auth> {
            val gatewayComponent =
                DaggerGatewayComponent
                    .builder()
                    .appComponent(LocalAppComponent.current)
                    .build()
            val factory = gatewayComponent.getGatewayViewModelFactory()
            val viewModel = viewModel<GatewayViewModel>(factory = factory)
        }

        composable<Votes> {
            val votesComponent =
                DaggerVotesComponent
                    .builder()
                    .appComponent(LocalAppComponent.current)
                    .build()
            val factory = votesComponent.getVotesViewModelFactory()
            val viewModel = viewModel<VotesViewModel>(factory = factory)
            navHostController.navigate(
                VotesScreen(
                    viewModel
                ) {
                    navHostController.navigate(Vote)
                }
            ) {
            }
        }

        composable<Vote> {
            val voteComponent =
                DaggerVoteComponent
                    .builder()
                    .appComponent(LocalAppComponent.current)
                    .build()
            val factory = voteComponent.getVoteViewModelFactory()
            val viewModel = viewModel<VoteViewModel>(factory = factory)
        }

        composable<CreateVote> {
            val createVoteComponent =
                DaggerCreateVoteComponent
                    .builder()
                    .appComponent(LocalAppComponent.current)
                    .build()
            val factory = createVoteComponent.getCreateVoteViewModelFactory()
            val viewModel = viewModel<CreateVoteViewModel>(factory = factory)
        }
    }
}