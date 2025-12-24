package ru.wolfram.votes.domain.model

import ru.wolfram.common.domain.model.Vote

sealed interface VotesState {
    data class Success(val votes: Map<String, List<Vote>>) : VotesState

    object Failure : VotesState

    object Loading : VotesState
}