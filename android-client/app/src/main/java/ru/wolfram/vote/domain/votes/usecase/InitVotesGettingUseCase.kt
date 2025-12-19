package ru.wolfram.vote.domain.votes.usecase

import ru.wolfram.vote.domain.votes.repository.VotesRepository
import javax.inject.Inject

class InitVotesGettingUseCase @Inject constructor(
    private val repository: VotesRepository
) {
    suspend operator fun invoke() = repository.initVotesGetting()
}