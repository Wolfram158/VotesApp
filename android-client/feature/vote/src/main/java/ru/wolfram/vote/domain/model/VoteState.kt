package ru.wolfram.vote.domain.model

import ru.wolfram.common.domain.model.Vote

sealed interface VoteState {
    data class Success(val vote: List<Vote>) : VoteState

    object Failure : VoteState

    object Loading : VoteState
}