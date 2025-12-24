package ru.wolfram.votes.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.wolfram.common.di.DispatchersIOQualifier
import ru.wolfram.votes.di.VotesScope
import ru.wolfram.votes.domain.model.VotesState
import ru.wolfram.votes.domain.usecase.GetVotesFlowUseCase
import ru.wolfram.votes.domain.usecase.InitVotesGettingUseCase
import javax.inject.Inject

@VotesScope
class VotesViewModel @Inject internal constructor(
    getVotesFlowUseCase: GetVotesFlowUseCase,
    private val initVotesGettingUseCase: InitVotesGettingUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val votes =
        getVotesFlowUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                VotesState.Loading
            )

    init {
        initVotesGetting()
    }

    fun initVotesGetting() {
        viewModelScope.launch(ioDispatcher) {
            Log.e(tag, "votes getting")
            initVotesGettingUseCase()
        }
    }

    companion object {
        private val tag by lazy {
            VotesViewModel::class.simpleName
        }
    }
}