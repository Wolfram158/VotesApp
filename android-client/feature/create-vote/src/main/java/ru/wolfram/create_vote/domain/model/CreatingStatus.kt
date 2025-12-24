package ru.wolfram.create_vote.domain.model

import ru.wolfram.create_vote.presentation.CreateVoteVariant

sealed interface CreatingStatus {
    object Initial : CreatingStatus

    object Success : CreatingStatus

    object Error : CreatingStatus

    data class Edit(val variant: CreateVoteVariant) : CreatingStatus

    object New : CreatingStatus
}