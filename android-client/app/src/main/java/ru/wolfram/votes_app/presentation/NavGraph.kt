package ru.wolfram.votes_app.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.wolfram.common.presentation.CreateVote
import ru.wolfram.common.presentation.Gateway
import ru.wolfram.common.presentation.RefreshWithEmailCode
import ru.wolfram.common.presentation.RegistrationForEmailCode
import ru.wolfram.common.presentation.RegistrationWithEmailCode
import ru.wolfram.common.presentation.Vote
import ru.wolfram.common.presentation.Votes
import ru.wolfram.create_vote.presentation.CreateVoteScreenPublic
import ru.wolfram.gateway.presentation.GatewayScreenPublic
import ru.wolfram.refresh_with_email_code.presentation.RefreshWithEmailCodeScreenPublic
import ru.wolfram.registration_for_email_code.presentation.RegistrationForEmailCodeScreenPublic
import ru.wolfram.registration_with_email_code.presentation.RegistrationWithEmailCodeScreenPublic
import ru.wolfram.votes.presentation.VotesScreenPublic

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Gateway
    ) {
        composable<Gateway> { entry ->
            GatewayScreenPublic(navHostController, entry)
        }

        composable<RegistrationForEmailCode> { entry ->
            RegistrationForEmailCodeScreenPublic(navHostController, entry)
        }

        composable<RegistrationWithEmailCode> { entry ->
            RegistrationWithEmailCodeScreenPublic(navHostController, entry)
        }

        composable<RefreshWithEmailCode> { entry ->
            RefreshWithEmailCodeScreenPublic(navHostController, entry)
        }

        composable<Votes> { entry ->
            VotesScreenPublic(navHostController, entry)
        }

        composable<Vote> { entry ->
            VoteScreenPublic(navHostController, entry)
        }

        composable<CreateVote> { entry ->
            CreateVoteScreenPublic(navHostController, entry)
        }
    }
}