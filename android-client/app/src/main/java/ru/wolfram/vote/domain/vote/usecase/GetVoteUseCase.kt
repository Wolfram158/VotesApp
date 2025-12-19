package ru.wolfram.vote.domain.vote.usecase

import ru.wolfram.vote.domain.vote.repository.VoteRepository
import javax.inject.Inject

class GetVoteUseCase @Inject constructor(
    private val repository: VoteRepository
) {
    suspend operator fun invoke(title: String) = repository.getVoteFlow(title)
}