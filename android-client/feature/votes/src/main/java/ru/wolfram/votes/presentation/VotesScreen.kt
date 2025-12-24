package ru.wolfram.votes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.wolfram.votes_app.presentation.ProgressScreen
import ru.wolfram.votes.domain.model.VotesState

@Composable
fun VotesScreen(
    votesViewModel: VotesViewModel,
    onNavigateToVote: (title: String) -> Unit,
    onNavigateToCreateVote: () -> Unit
) {
    val votes = votesViewModel.votes.collectAsState()

    when (val votesValue = votes.value) {
        is VotesState.Success -> {
            VotesSuccessScreen(
                votesValue.votes.keys.toList(),
                votesViewModel::initVotesGetting,
                onNavigateToVote,
                onNavigateToCreateVote
            )
        }

        VotesState.Failure -> {
            VotesFailureScreen(
                votesViewModel::initVotesGetting
            )
        }

        VotesState.Loading -> {
            ProgressScreen()
        }
    }
}