package ru.wolfram.vote.data.votes.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.wolfram.vote.data.network.dto.toVoteMap
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.domain.votes.model.Vote
import ru.wolfram.vote.domain.votes.repository.VotesRepository
import javax.inject.Inject

class VotesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : VotesRepository {
    private val emission = MutableSharedFlow<Unit>()

    override fun getVotesFlow(): Flow<Result<Map<String, List<Vote>>>> {
        return flow {
            emission.collect {
                try {
                    emit(
                        Result.success(
                            apiService.getVotes(
                                accessTokenStore.data.firstOrNull()?.token
                                    ?: throw RuntimeException("Access token must be non-nullable!")
                            ).toVoteMap()
                        )
                    )
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }
        }.also {
            CoroutineScope(ioDispatcher).launch {
                delay(200)
                initVotesGetting()
            }
        }
    }

    override suspend fun initVotesGetting() {
        emission.emit(Unit)
    }
}