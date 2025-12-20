package ru.wolfram.vote.presentation

sealed interface TryToEnterState {
    object Initial : TryToEnterState
    object Failure : TryToEnterState
    object Accept : TryToEnterState
    object Reject : TryToEnterState
}