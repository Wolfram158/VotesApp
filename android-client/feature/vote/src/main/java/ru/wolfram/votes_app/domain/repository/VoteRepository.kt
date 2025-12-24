package ru.wolfram.votes_app.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.votes_app.domain.model.VoteState

interface VoteRepository {
    fun getVoteFlow(): Flow<VoteState>

    suspend fun initVoteGetting(title: String)

    suspend fun doVote(title: String, variant: String)
}