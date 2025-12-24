package ru.wolfram.vote.domain.votes.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.vote.domain.votes.model.VotesState

interface VotesRepository {
    fun getVotesFlow(): Flow<VotesState>

    suspend fun initVotesGetting()
}