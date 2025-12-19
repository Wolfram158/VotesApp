package ru.wolfram.vote.domain.refresh_with_email_code.usecase

import ru.wolfram.vote.domain.refresh_with_email_code.repository.RefreshWithEmailCodeRepository
import javax.inject.Inject

class RefreshWithEmailCodeUseCase @Inject constructor(
    private val repository: RefreshWithEmailCodeRepository
) {
    suspend operator fun invoke() =
        repository.refreshWithEmailCode()
}