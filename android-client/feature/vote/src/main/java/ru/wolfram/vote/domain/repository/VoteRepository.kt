package ru.wolfram.vote.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.vote.domain.model.VoteState

interface VoteRepository {
    fun getVoteFlow(): Flow<VoteState>

    suspend fun initVoteGetting(title: String)

    suspend fun doVote(title: String, variant: String)
}