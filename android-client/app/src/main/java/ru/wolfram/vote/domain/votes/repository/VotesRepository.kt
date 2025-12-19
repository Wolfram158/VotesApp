package ru.wolfram.vote.domain.votes.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.vote.domain.votes.model.Vote

interface VotesRepository {
    fun getVotesFlow(): Flow<Result<Map<String, List<Vote>>>>

    suspend fun initVotesGetting()
}