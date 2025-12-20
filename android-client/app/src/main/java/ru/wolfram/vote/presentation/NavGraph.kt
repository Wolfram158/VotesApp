package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
        composable<Auth> { entry ->
            val appComponent = LocalAppComponent.current
            val gatewayComponent =
                remember(entry) {
                    DaggerGatewayComponent
                        .builder()
                        .appComponent(appComponent)
                        .build()
                }
            val factory = remember(gatewayComponent) {
                gatewayComponent.getGatewayViewModelFactory()
            }
            val viewModel = viewModel<GatewayViewModel>(factory = factory)
        }

        composable<Votes> { entry ->
            val appComponent = LocalAppComponent.current
            val votesComponent =
                remember(entry) {
                    DaggerVotesComponent
                        .builder()
                        .appComponent(appComponent)
                        .build()
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

        composable<Vote> { entry ->
            val route = entry.toRoute<Vote>()
            val appComponent = LocalAppComponent.current
            val voteComponent =
                remember(entry) {
                    DaggerVoteComponent
                        .builder()
                        .appComponent(appComponent)
                        .build()
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

        composable<CreateVote> { entry ->
            val appComponent = LocalAppComponent.current
            val createVoteComponent =
                remember(entry) {
                    DaggerCreateVoteComponent
                        .builder()
                        .appComponent(appComponent)
                        .build()
                }
            val factory = remember(createVoteComponent) {
                createVoteComponent.getCreateVoteViewModelFactory()
            }
            val viewModel = viewModel<CreateVoteViewModel>(factory = factory)
            CreateVoteScreen(
                viewModel,
                { title, variants ->
                    viewModel.createVote(title, variants)
                }
            ) {
                navHostController.popBackStack()
            }
        }
    }
}
