package ru.wolfram.vote.data.votes.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import ru.wolfram.vote.data.network.dto.toVoteMap
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.domain.votes.model.Vote
import ru.wolfram.vote.domain.votes.repository.VotesRepository
import javax.inject.Inject

class VotesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>
) : VotesRepository {
    override suspend fun getVotes(): Map<String, List<Vote>> {
        return apiService.getVotes(
            accessTokenStore.data.firstOrNull()?.token
                ?: throw RuntimeException("Access token must be non-nullable!")
        ).toVoteMap()
    }

}