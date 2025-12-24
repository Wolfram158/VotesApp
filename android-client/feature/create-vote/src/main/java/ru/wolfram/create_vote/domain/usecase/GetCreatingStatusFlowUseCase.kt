package ru.wolfram.create_vote.domain.usecase

import ru.wolfram.create_vote.domain.repository.CreateVoteRepository
import javax.inject.Inject

internal class GetCreatingStatusFlowUseCase @Inject constructor(
    private val repository: CreateVoteRepository
) {
    operator fun invoke() = repository.getCreatingStatusFlow()
}