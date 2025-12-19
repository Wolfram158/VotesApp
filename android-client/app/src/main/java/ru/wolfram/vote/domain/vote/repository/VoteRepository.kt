package ru.wolfram.vote.domain.vote.repository

import ru.wolfram.vote.domain.votes.model.Vote

interface VoteRepository {
    suspend fun getVote(title: String): List<Vote>

    suspend fun doVote(title: String, variant: String): List<Vote>
}