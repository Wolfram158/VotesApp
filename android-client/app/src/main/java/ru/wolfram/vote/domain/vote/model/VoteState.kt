package ru.wolfram.vote.domain.vote.model

import ru.wolfram.vote.domain.votes.model.Vote

sealed interface VoteState {
    data class Success(val vote: List<Vote>) : VoteState

    object Failure : VoteState

    object Loading : VoteState
}