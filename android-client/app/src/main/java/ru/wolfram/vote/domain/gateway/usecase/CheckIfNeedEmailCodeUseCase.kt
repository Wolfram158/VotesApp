package ru.wolfram.vote.domain.gateway.usecase

import ru.wolfram.vote.domain.gateway.repository.RefreshForEmailCodeRepository
import javax.inject.Inject

class CheckIfNeedEmailCodeUseCase @Inject constructor(
    private val repository: RefreshForEmailCodeRepository
) {
    suspend operator fun invoke(username: String) = repository.checkIfNeedEmailCode(username)
}