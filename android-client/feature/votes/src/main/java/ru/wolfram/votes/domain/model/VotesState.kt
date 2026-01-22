package ru.wolfram.votes.domain.model

sealed interface VotesState {
    data class Success(val votes: List<String>) : VotesState

    object Failure : VotesState

    object Loading : VotesState
}