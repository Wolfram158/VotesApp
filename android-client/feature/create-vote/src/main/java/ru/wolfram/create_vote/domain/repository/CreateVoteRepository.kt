package ru.wolfram.create_vote.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.create_vote.domain.model.CreatingStatus

interface CreateVoteRepository {
    fun getCreatingStatusFlow(): Flow<CreatingStatus>

    suspend fun createVote(title: String, variants: Set<String>)
}