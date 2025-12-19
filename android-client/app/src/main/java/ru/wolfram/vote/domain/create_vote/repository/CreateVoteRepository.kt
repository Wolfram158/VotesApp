package ru.wolfram.vote.domain.create_vote.repository

interface CreateVoteRepository {
    suspend fun createVote(title: String, variants: Set<String>)
}