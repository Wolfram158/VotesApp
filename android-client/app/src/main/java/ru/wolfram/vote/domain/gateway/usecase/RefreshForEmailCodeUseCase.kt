package ru.wolfram.vote.domain.gateway.usecase

import ru.wolfram.vote.domain.gateway.model.RefreshForEmailCodeContainer
import ru.wolfram.vote.domain.gateway.repository.RefreshForEmailCodeRepository
import javax.inject.Inject

class RefreshForEmailCodeUseCase @Inject constructor(
    private val repository: RefreshForEmailCodeRepository
) {
    suspend operator fun invoke(container: RefreshForEmailCodeContainer) =
        repository.refreshForEmailCode(container)
}