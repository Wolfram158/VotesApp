package ru.wolfram.votes_app.domain.model

import ru.wolfram.common.domain.model.Vote

sealed interface VoteState {
    data class Success(val vote: List<Vote>) : VoteState

    object Failure : VoteState

    object Loading : VoteState
}