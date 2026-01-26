package ru.wolfram.votes_app.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.wolfram.common.presentation.ProgressScreen
import ru.wolfram.votes_app.domain.model.VoteState

@Composable
internal fun VoteScreen(
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