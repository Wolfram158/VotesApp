package ru.wolfram.vote.domain.vote.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.vote.domain.vote.model.VoteState

interface VoteRepository {
    fun getVoteFlow(): Flow<VoteState>

    suspend fun initVoteGetting(title: String)

    suspend fun doVote(title: String, variant: String)
}