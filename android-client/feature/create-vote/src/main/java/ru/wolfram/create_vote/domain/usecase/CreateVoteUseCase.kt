package ru.wolfram.create_vote.domain.usecase

import ru.wolfram.create_vote.domain.repository.CreateVoteRepository
import javax.inject.Inject

internal class CreateVoteUseCase @Inject constructor(
    private val repository: CreateVoteRepository
) {
    suspend operator fun invoke(title: String, variants: Set<String>) =
        repository.createVote(title, variants)
}