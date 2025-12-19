package ru.wolfram.vote.data.vote.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import ru.wolfram.vote.data.network.dto.VoteDto
import ru.wolfram.vote.data.network.dto.toVotes
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.domain.vote.repository.VoteRepository
import ru.wolfram.vote.domain.votes.model.Vote
import javax.inject.Inject

class VoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>
) : VoteRepository {
    override suspend fun getVoteFlow(title: String): Flow<Result<List<Vote>>> {
        val token = accessTokenStore.data.firstOrNull()?.token
            ?: throw RuntimeException("Access token must be non-nullable!")
        return apiService.getVote(title, token).toVotes()
    }

    override suspend fun doVote(
        title: String,
        variant: String
    ): List<Vote> {
        val token = accessTokenStore.data.firstOrNull()?.token
            ?: throw RuntimeException("Access token must be non-nullable!")
        return apiService.doVote(VoteDto(title, variant), token).toVotes()
    }

}