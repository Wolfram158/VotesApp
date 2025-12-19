package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun VotesScreen(
    votesViewModel: VotesViewModel,
    onNavigateToVote: () -> Unit
) {
    val votes = votesViewModel.votes.collectAsState()
    val votesValue = votes.value

    when (votesValue.isSuccess) {
        true -> {
            VotesSuccessScreen(
                votesValue.getOrDefault(mapOf()).keys.toList(),
                onNavigateToVote
            )
        }

        false -> {
            VotesFailureScreen(
                votesViewModel::initVotesGetting
            )
        }
    }
}