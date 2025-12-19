package ru.wolfram.vote.domain.votes.repository

import ru.wolfram.vote.domain.votes.model.Vote

interface VotesRepository {
    suspend fun getVotes(): Map<String, List<Vote>>
}