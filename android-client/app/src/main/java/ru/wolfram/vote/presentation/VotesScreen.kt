package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun VotesScreen(
    votesViewModel: VotesViewModel,
    onNavigateToVote: (title: String) -> Unit,
    onNavigateToCreateVote: () -> Unit
) {
    val votes = votesViewModel.votes.collectAsState()
    val votesValue = votes.value

    when (votesValue.isSuccess) {
        true -> {
            VotesSuccessScreen(
                votesValue.getOrDefault(mapOf()).keys.toList(),
                votesViewModel::initVotesGetting,
                onNavigateToVote,
                onNavigateToCreateVote
            )
        }

        false -> {
            VotesFailureScreen(
                votesViewModel::initVotesGetting
            )
        }
    }
}