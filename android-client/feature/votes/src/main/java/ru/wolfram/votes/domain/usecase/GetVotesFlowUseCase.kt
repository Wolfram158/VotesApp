package ru.wolfram.votes.domain.usecase

import ru.wolfram.votes.domain.repository.VotesRepository
import javax.inject.Inject

internal class GetVotesFlowUseCase @Inject constructor(
    private val repository: VotesRepository
) {
    operator fun invoke() = repository.getVotesFlow()
}