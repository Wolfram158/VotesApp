package ru.wolfram.votes.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.wolfram.common.data.network.dto.toVoteMap
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.votes.domain.model.VotesState
import ru.wolfram.votes.domain.repository.VotesRepository
import javax.inject.Inject

internal class VotesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>
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
                                accessTokenStore.data.firstOrNull()?.token
                                    ?: throw RuntimeException("Access token must be non-nullable!")
                            ).toVoteMap()
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