package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.wolfram.vote.domain.votes.model.VotesState

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