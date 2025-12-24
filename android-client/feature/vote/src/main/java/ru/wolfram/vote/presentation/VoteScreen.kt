package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.wolfram.vote.domain.model.VoteState

@Composable
fun VoteScreen(
    voteViewModel: VoteViewModel,
    title: String
) {
    val votes = voteViewModel.vote.collectAsState()

    when (val voteValue = votes.value) {
        is VoteState.Success -> {
            VoteSuccessScreen(
                voteValue.vote,
                {
                    voteViewModel.initVoteGetting(title)
                }
            ) { title, variant ->
                voteViewModel.doVote(title, variant)
            }
        }

        VoteState.Failure -> {
            VoteFailureScreen {
                voteViewModel.initVoteGetting(title)
            }
        }

        VoteState.Loading -> {
            ProgressScreen()
        }
    }
}