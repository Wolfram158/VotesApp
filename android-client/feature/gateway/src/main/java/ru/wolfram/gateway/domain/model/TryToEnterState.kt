package ru.wolfram.gateway.domain.model

sealed interface TryToEnterState {
    object Initial : TryToEnterState
    object Failure : TryToEnterState
    object Accept : TryToEnterState
    object Reject : TryToEnterState
}