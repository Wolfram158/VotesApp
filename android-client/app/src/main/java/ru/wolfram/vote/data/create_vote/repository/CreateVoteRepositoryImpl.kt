package ru.wolfram.vote.data.create_vote.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.wolfram.vote.data.network.dto.VoteDto
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.domain.create_vote.model.CreatingStatus
import ru.wolfram.vote.domain.create_vote.repository.CreateVoteRepository
import javax.inject.Inject

class CreateVoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>
) : CreateVoteRepository {
    private val creatingStatus = MutableSharedFlow<CreatingStatus>()

    override fun getCreatingStatusFlow(): Flow<CreatingStatus> {
        return flow {
            creatingStatus.collect {
                emit(it)
            }
        }
    }

    override suspend fun createVote(
        title: String,
        variants: Set<String>
    ) {
        try {
            val token = accessTokenStore.data.firstOrNull()?.token
                ?: throw RuntimeException("Access token must be non-nullable!")
            apiService.createVote(variants.map { VoteDto(title, it) }, token)
            creatingStatus.emit(CreatingStatus.Success)
        } catch (_: Exception) {
            creatingStatus.emit(CreatingStatus.Error)
        }
    }
}