package ru.wolfram.vote.domain.vote.usecase

import ru.wolfram.vote.domain.vote.repository.VoteRepository
import javax.inject.Inject

class GetVoteFlowUseCase @Inject constructor(
    private val repository: VoteRepository
) {
    operator fun invoke() = repository.getVoteFlow()
}