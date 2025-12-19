package ru.wolfram.vote.domain.create_vote.usecase

import ru.wolfram.vote.domain.create_vote.repository.CreateVoteRepository
import javax.inject.Inject

class GetCreatingStatusFlowUseCase @Inject constructor(
    private val repository: CreateVoteRepository
) {
    operator fun invoke() = repository.getCreatingStatusFlow()
}