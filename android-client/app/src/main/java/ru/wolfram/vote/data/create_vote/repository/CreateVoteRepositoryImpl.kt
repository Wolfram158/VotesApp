package ru.wolfram.vote.data.create_vote.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import ru.wolfram.vote.data.network.dto.VoteDto
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.domain.create_vote.repository.CreateVoteRepository
import javax.inject.Inject

class CreateVoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>
) : CreateVoteRepository {
    override suspend fun createVote(
        title: String,
        variants: Set<String>
    ) {
        val token = accessTokenStore.data.firstOrNull()?.token
            ?: throw RuntimeException("Access token must be non-nullable!")
        apiService.createVote(variants.map { VoteDto(title, it) }, token)
    }
}