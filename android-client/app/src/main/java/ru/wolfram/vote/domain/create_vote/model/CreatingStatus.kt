package ru.wolfram.vote.domain.create_vote.model

import ru.wolfram.vote.presentation.CreateVoteVariant

sealed interface CreatingStatus {
    object Initial : CreatingStatus

    object Success : CreatingStatus

    object Error : CreatingStatus

    data class Edit(val variant: CreateVoteVariant) : CreatingStatus

    object New : CreatingStatus
}