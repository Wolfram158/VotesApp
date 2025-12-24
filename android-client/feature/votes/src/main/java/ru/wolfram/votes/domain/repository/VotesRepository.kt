package ru.wolfram.votes.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.votes.domain.model.VotesState

interface VotesRepository {
    fun getVotesFlow(): Flow<VotesState>

    suspend fun initVotesGetting()
}