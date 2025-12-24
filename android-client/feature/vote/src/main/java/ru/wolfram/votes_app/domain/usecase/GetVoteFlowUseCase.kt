package ru.wolfram.votes_app.domain.usecase

import ru.wolfram.votes_app.domain.repository.VoteRepository
import javax.inject.Inject

internal class GetVoteFlowUseCase @Inject constructor(
    private val repository: VoteRepository
) {
    operator fun invoke() = repository.getVoteFlow()
}