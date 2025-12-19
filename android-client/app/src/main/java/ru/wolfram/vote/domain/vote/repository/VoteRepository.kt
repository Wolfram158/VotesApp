package ru.wolfram.vote.domain.vote.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.vote.domain.votes.model.Vote

interface VoteRepository {
    fun getVoteFlow(): Flow<Result<List<Vote>>>

    suspend fun initVoteGetting(title: String)

    suspend fun doVote(title: String, variant: String)
}