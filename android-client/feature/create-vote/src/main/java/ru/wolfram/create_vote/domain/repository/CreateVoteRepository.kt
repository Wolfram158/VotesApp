package ru.wolfram.vote.domain.create_vote.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.vote.domain.create_vote.model.CreatingStatus

interface CreateVoteRepository {
    fun getCreatingStatusFlow(): Flow<CreatingStatus>

    suspend fun createVote(title: String, variants: Set<String>)
}