package ru.wolfram.votes_app.domain.usecase

import ru.wolfram.votes_app.domain.repository.VoteRepository
import javax.inject.Inject

internal class InitVoteGettingUseCase @Inject constructor(
    private val repository: VoteRepository
) {
    suspend operator fun invoke(title: String) = repository.initVoteGetting(title)
}