package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import ru.wolfram.vote.di.VoteScope
import ru.wolfram.vote.domain.vote.usecase.DoVoteUseCase
import ru.wolfram.vote.domain.vote.usecase.GetVoteUseCase
import javax.inject.Inject

@VoteScope
class VoteViewModel @Inject constructor(
    private val getVoteUseCase: GetVoteUseCase,
    private val doVoteUseCase: DoVoteUseCase
) : ViewModel() {

}