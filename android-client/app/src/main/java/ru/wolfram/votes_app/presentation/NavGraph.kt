package ru.wolfram.votes_app.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.wolfram.create_vote.di.DaggerCreateVoteComponent
import ru.wolfram.create_vote.presentation.CreateVote
import ru.wolfram.create_vote.presentation.CreateVoteScreen
import ru.wolfram.create_vote.presentation.CreateVoteViewModel
import ru.wolfram.gateway.di.DaggerGatewayComponent
import ru.wolfram.gateway.presentation.Gateway
import ru.wolfram.gateway.presentation.GatewayScreen
import ru.wolfram.gateway.presentation.GatewayViewModel
import ru.wolfram.refresh_with_email_code.di.DaggerRefreshWithEmailCodeComponent
import ru.wolfram.refresh_with_email_code.presentation.RefreshWithEmailCode
import ru.wolfram.refresh_with_email_code.presentation.RefreshWithEmailCodeScreen
import ru.wolfram.refresh_with_email_code.presentation.RefreshWithEmailCodeViewModel
import ru.wolfram.registration_for_email_code.di.DaggerRegistrationForEmailCodeComponent
import ru.wolfram.registration_for_email_code.presentation.RegistrationForEmailCode
import ru.wolfram.registration_for_email_code.presentation.RegistrationForEmailCodeScreen
import ru.wolfram.registration_for_email_code.presentation.RegistrationForEmailCodeViewModel
import ru.wolfram.registration_with_email_code.di.DaggerRegistrationWithEmailCodeComponent
import ru.wolfram.registration_with_email_code.presentation.RegistrationWithEmailCode
import ru.wolfram.registration_with_email_code.presentation.RegistrationWithEmailCodeScreen
import ru.wolfram.registration_with_email_code.presentation.RegistrationWithEmailCodeViewModel
import ru.wolfram.votes.di.DaggerVotesComponent
import ru.wolfram.votes.presentation.Votes
import ru.wolfram.votes.presentation.VotesScreen
import ru.wolfram.votes.presentation.VotesViewModel
import ru.wolfram.votes_app.di.DaggerVoteComponent
import kotlin.reflect.KClass

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Gateway
    ) {
        composable<Gateway> { entry ->
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

        composable<RegistrationForEmailCode> { entry ->
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

        composable<RegistrationWithEmailCode> { entry ->
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

        composable<RefreshWithEmailCode> { entry ->
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

        composable<Votes> { entry ->
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

        composable<Vote> { entry ->
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

        composable<CreateVote> { entry ->
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
    }
}

private const val COMPONENT_CREATION_TAG = "COMPONENT_CREATION"

fun <T : Any> logCreation(kClass: KClass<T>, tag: String = COMPONENT_CREATION_TAG) {
    Log.d(tag, "${kClass.simpleName} is created")
}