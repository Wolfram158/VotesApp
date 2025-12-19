package ru.wolfram.vote.domain.create_vote.model

sealed interface CreatingStatus {
    object Initial : CreatingStatus

    object Success : CreatingStatus

    object Error : CreatingStatus
}