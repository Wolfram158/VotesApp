package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.wolfram.vote.di.CreateVoteScope
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.domain.create_vote.usecase.CreateVoteUseCase
import ru.wolfram.vote.domain.create_vote.usecase.GetCreatingStatusFlowUseCase
import javax.inject.Inject

@CreateVoteScope
class CreateVoteViewModel @Inject constructor(
    getCreatingStatusFlowUseCase: GetCreatingStatusFlowUseCase,
    private val createVoteUseCase: CreateVoteUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val creatingStatus =
        getCreatingStatusFlowUseCase()
            .shareIn(
                viewModelScope,
                SharingStarted.Lazily
            )

    fun createVote(title: String, variants: Set<String>) {
        viewModelScope.launch(ioDispatcher) {
            createVoteUseCase(title, variants)
        }
    }
}