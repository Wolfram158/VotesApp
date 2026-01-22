package ru.wolfram.votes.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.domain.storage.LocalDataStorage
import ru.wolfram.votes.domain.model.VotesState
import ru.wolfram.votes.domain.repository.VotesRepository
import javax.inject.Inject

internal class VotesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localDataStorage: LocalDataStorage
) : VotesRepository {
    private val emission = MutableSharedFlow<Unit>(replay = 1)

    override fun getVotesFlow(): Flow<VotesState> {
        return flow {
            emission.collect {
                try {
                    emit(VotesState.Loading)
                    emit(
                        VotesState.Success(
                            apiService.getVotes(
                                1,
                                localDataStorage.readAccessTokenPreferences()?.token
                                    ?: throw RuntimeException("Access token must be non-nullable!")
                            ).titles
                        )
                    )
                } catch (_: Exception) {
                    emit(VotesState.Failure)
                }
            }
        }
    }

    override suspend fun initVotesGetting() {
        emission.emit(Unit)
    }
}