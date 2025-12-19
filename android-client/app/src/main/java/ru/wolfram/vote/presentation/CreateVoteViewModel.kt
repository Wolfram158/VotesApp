package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import ru.wolfram.vote.di.CreateVoteScope
import ru.wolfram.vote.domain.create_vote.usecase.CreateVoteUseCase
import javax.inject.Inject

@CreateVoteScope
class CreateVoteViewModel @Inject constructor(
    private val createVoteUseCase: CreateVoteUseCase
) : ViewModel() {
}