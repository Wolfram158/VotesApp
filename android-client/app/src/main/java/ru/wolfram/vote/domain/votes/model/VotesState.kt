package ru.wolfram.vote.domain.votes.model

sealed interface VotesState {
    data class Success(val votes: Map<String, List<Vote>>) : VotesState

    object Failure : VotesState

    object Loading : VotesState
}