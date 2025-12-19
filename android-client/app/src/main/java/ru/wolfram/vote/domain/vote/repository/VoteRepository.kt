package ru.wolfram.vote.domain.vote.repository

import kotlinx.coroutines.flow.Flow
import ru.wolfram.vote.domain.votes.model.Vote

interface VoteRepository {
    suspend fun getVoteFlow(title: String): Flow<Result<List<Vote>>>

    suspend fun doVote(title: String, variant: String)
}