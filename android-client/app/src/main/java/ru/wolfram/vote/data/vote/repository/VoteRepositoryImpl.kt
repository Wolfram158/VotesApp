package ru.wolfram.vote.data.vote.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.wolfram.vote.data.network.dto.VoteDto
import ru.wolfram.vote.data.network.dto.toVotes
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.domain.vote.model.VoteState
import ru.wolfram.vote.domain.vote.repository.VoteRepository
import javax.inject.Inject

class VoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>
) : VoteRepository {
    private val emission = MutableSharedFlow<ActionType>(replay = 1)

    override fun getVoteFlow(): Flow<VoteState> {
        return flow {
            emission.collect {
                try {
                    when (it) {
                        is ActionType.DoVote -> {
                            val token = accessTokenStore.data.firstOrNull()?.token
                                ?: throw RuntimeException("Access token must be non-nullable!")
                            emit(
                                VoteState.Success(
                                    apiService.doVote(
                                        VoteDto(it.title, it.variant),
                                        token
                                    ).toVotes()
                                )
                            )
                        }

                        is ActionType.GetVote -> {
                            emit(VoteState.Loading)
                            val token = accessTokenStore.data.firstOrNull()?.token
                                ?: throw RuntimeException("Access token must be non-nullable!")
                            emit(VoteState.Success(apiService.getVote(it.title, token).toVotes()))
                        }
                    }
                } catch (_: Exception) {
                    emit(VoteState.Failure)
                }
            }
        }
    }

    override suspend fun initVoteGetting(title: String) {
        emission.emit(ActionType.GetVote(title))
    }

    override suspend fun doVote(
        title: String,
        variant: String
    ) {
        emission.emit(ActionType.DoVote(title, variant))
    }

    private sealed interface ActionType {
        data class DoVote(
            val title: String,
            val variant: String
        ) : ActionType

        data class GetVote(
            val title: String
        ) : ActionType
    }

}