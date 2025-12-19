package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.di.VotesScope
import ru.wolfram.vote.domain.votes.usecase.GetVotesFlowUseCase
import ru.wolfram.vote.domain.votes.usecase.InitVotesGettingUseCase
import javax.inject.Inject

@VotesScope
class VotesViewModel @Inject constructor(
    getVotesFlowUseCase: GetVotesFlowUseCase,
    private val initVotesGettingUseCase: InitVotesGettingUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val votes =
        getVotesFlowUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                Result.success(mapOf())
            )

    fun initVotesGetting() {
        viewModelScope.launch(ioDispatcher) {
            initVotesGettingUseCase()
        }
    }
}