package ru.wolfram.create_vote.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import ru.wolfram.common.data.network.dto.VoteDto
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.domain.storage.LocalDataStorage
import ru.wolfram.create_vote.domain.model.CreatingStatus
import ru.wolfram.create_vote.domain.repository.CreateVoteRepository
import javax.inject.Inject

internal class CreateVoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localDataStorage: LocalDataStorage
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
            val token = localDataStorage.readAccessTokenPreferences()?.token
                ?: throw RuntimeException("Access token must be non-nullable!")
            val response = apiService.createVote(variants.map { VoteDto(title, it) }, token)
            Log.e(tag, "response code: ${response.code()}")
            if (response.code() == 200) {
                creatingStatus.emit(CreatingStatus.Success)
            } else {
                creatingStatus.emit(CreatingStatus.Error)
            }
        } catch (_: Exception) {
            creatingStatus.emit(CreatingStatus.Error)
        }
    }

    companion object {
        private val tag = this::class.simpleName
    }
}