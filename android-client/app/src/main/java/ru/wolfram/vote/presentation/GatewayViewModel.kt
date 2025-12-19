package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import ru.wolfram.vote.di.GatewayScope
import ru.wolfram.vote.domain.gateway.usecase.RefreshForEmailCodeUseCase
import javax.inject.Inject

@GatewayScope
class GatewayViewModel @Inject constructor(
    private val refreshForEmailCodeUseCase: RefreshForEmailCodeUseCase
) : ViewModel() {
}