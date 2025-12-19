package ru.wolfram.vote.domain.votes.usecase

import ru.wolfram.vote.domain.votes.repository.VotesRepository
import javax.inject.Inject

class GetVotesFlowUseCase @Inject constructor(
    private val repository: VotesRepository
) {
    operator fun invoke() = repository.getVotesFlow()
}