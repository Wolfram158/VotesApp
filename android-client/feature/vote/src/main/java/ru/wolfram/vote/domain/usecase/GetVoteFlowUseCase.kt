package ru.wolfram.vote.domain.usecase

import ru.wolfram.vote.domain.repository.VoteRepository
import javax.inject.Inject

internal class GetVoteFlowUseCase @Inject constructor(
    private val repository: VoteRepository
) {
    operator fun invoke() = repository.getVoteFlow()
}