package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.di.VoteScope
import ru.wolfram.vote.domain.vote.usecase.DoVoteUseCase
import ru.wolfram.vote.domain.vote.usecase.GetVoteFlowUseCase
import ru.wolfram.vote.domain.vote.usecase.InitVoteGettingUseCase
import javax.inject.Inject

@VoteScope
class VoteViewModel @Inject constructor(
    getVoteFlowUseCase: GetVoteFlowUseCase,
    private val doVoteUseCase: DoVoteUseCase,
    private val initVoteGettingUseCase: InitVoteGettingUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val vote =
        getVoteFlowUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                Result.success(emptyList())
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