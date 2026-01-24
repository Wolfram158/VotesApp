package ru.wolfram.votes_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.wolfram.common.di.DispatchersIOQualifier
import ru.wolfram.votes_app.di.VoteScope
import ru.wolfram.votes_app.domain.model.VoteState
import ru.wolfram.votes_app.domain.usecase.DoVoteUseCase
import ru.wolfram.votes_app.domain.usecase.GetVoteFlowUseCase
import ru.wolfram.votes_app.domain.usecase.InitVoteGettingUseCase
import javax.inject.Inject

@VoteScope
internal class VoteViewModel @Inject constructor(
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