package ru.wolfram.votes.domain.usecase

import ru.wolfram.votes.domain.repository.VotesRepository
import javax.inject.Inject

internal class InitVotesGettingUseCase @Inject constructor(
    private val repository: VotesRepository
) {
    suspend operator fun invoke() = repository.initVotesGetting()
}