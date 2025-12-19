package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import ru.wolfram.vote.domain.votes.model.Vote

@Composable
fun VoteSuccessScreen(
    vote: List<Vote>,
    onDoVote: (title: String, variant: String) -> Unit
) {

}