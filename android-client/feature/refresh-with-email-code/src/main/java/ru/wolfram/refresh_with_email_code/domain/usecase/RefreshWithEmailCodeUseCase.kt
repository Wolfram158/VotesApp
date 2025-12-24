package ru.wolfram.refresh_with_email_code.domain.usecase

import ru.wolfram.refresh_with_email_code.domain.model.RefreshWithEmailCodeContainer
import ru.wolfram.refresh_with_email_code.domain.repository.RefreshWithEmailCodeRepository
import javax.inject.Inject

internal class RefreshWithEmailCodeUseCase @Inject constructor(
    private val repository: RefreshWithEmailCodeRepository
) {
    suspend operator fun invoke(container: RefreshWithEmailCodeContainer) =
        repository.refreshWithEmailCode(container)
}