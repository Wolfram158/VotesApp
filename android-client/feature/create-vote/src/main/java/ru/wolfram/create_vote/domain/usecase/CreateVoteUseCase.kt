package ru.wolfram.vote.domain.create_vote.usecase

import ru.wolfram.vote.domain.create_vote.repository.CreateVoteRepository
import javax.inject.Inject

class CreateVoteUseCase @Inject constructor(
    private val repository: CreateVoteRepository
) {
    suspend operator fun invoke(title: String, variants: Set<String>) =
        repository.createVote(title, variants)
}