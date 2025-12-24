package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.wolfram.common.di.DispatchersIOQualifier
import ru.wolfram.vote.di.VoteScope
import ru.wolfram.vote.domain.model.VoteState
import ru.wolfram.vote.domain.usecase.DoVoteUseCase
import ru.wolfram.vote.domain.usecase.GetVoteFlowUseCase
import ru.wolfram.vote.domain.usecase.InitVoteGettingUseCase
import javax.inject.Inject

@VoteScope
class VoteViewModel @Inject internal constructor(
    getVoteFlowUseCase: GetVoteFlowUseCase,
    private val doVoteUseCase: DoVoteUseCase,
    private val initVoteGettingUseCase: InitVoteGettingUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    internal val vote =
        getVoteFlowUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                VoteState.Loading
            )

    fun initVoteGetting(title: String) {
        viewModelScope.launch(ioDispatcher) {
            initVoteGettingUseCase(title)
        }
    }

    fun doVote(title: String, variant: String) {
        viewModelScope.launch(ioDispatcher) {
            doVoteUseCase(title, variant)
        }
    }
}