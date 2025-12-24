package ru.wolfram.gateway.domain.usecase

import ru.wolfram.gateway.domain.repository.RefreshForEmailCodeRepository
import javax.inject.Inject

internal class CheckIfNeedEmailCodeUseCase @Inject constructor(
    private val repository: RefreshForEmailCodeRepository
) {
    suspend operator fun invoke(username: String) = repository.checkIfNeedEmailCode(username)
}