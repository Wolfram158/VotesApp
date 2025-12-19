package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun VoteScreen(
    voteViewModel: VoteViewModel,
    title: String
) {
    val votes = voteViewModel.vote.collectAsState()
    val voteValue = votes.value

    when (voteValue.isSuccess) {
        true -> {
            VoteSuccessScreen(
                voteValue.getOrDefault(listOf())
            ) { title, variant ->
                voteViewModel.doVote(title, variant)
            }
        }

        false -> {
            VoteFailureScreen {
                voteViewModel.initVoteGetting(title)
            }
        }
    }
}