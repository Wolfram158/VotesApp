package ru.wolfram.votes_app.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import ru.wolfram.common.data.network.dto.VoteDto
import ru.wolfram.common.data.network.dto.toVotes
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.domain.storage.LocalDataStorage
import ru.wolfram.votes_app.domain.model.VoteState
import ru.wolfram.votes_app.domain.repository.VoteRepository
import javax.inject.Inject

internal class VoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localDataStorage: LocalDataStorage
) : VoteRepository {
    private val emission = MutableSharedFlow<ActionType>(replay = 1)

    override fun getVoteFlow(): Flow<VoteState> {
        return flow {
            emission.collect {
                try {
                    when (it) {
                        is ActionType.DoVote -> {
                            val token = localDataStorage.readAccessTokenPreferences()?.token
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
                            val token = localDataStorage.readAccessTokenPreferences()?.token
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