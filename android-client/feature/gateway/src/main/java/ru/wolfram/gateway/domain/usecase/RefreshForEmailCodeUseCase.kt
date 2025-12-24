package ru.wolfram.gateway.domain.usecase

import ru.wolfram.gateway.domain.model.RefreshForEmailCodeContainer
import ru.wolfram.gateway.domain.repository.RefreshForEmailCodeRepository
import javax.inject.Inject

internal class RefreshForEmailCodeUseCase @Inject constructor(
    private val repository: RefreshForEmailCodeRepository
) {
    suspend operator fun invoke(container: RefreshForEmailCodeContainer) =
        repository.refreshForEmailCode(container)
}