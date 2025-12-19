package ru.wolfram.vote.domain.registration_for_email_code.usecase

import ru.wolfram.vote.domain.registration_for_email_code.model.RegistrationForEmailCodeContainer
import ru.wolfram.vote.domain.registration_for_email_code.repository.RegistrationForEmailCodeRepository
import javax.inject.Inject

class RegisterForEmailCodeUseCase @Inject constructor(
    private val repository: RegistrationForEmailCodeRepository
) {
    suspend operator fun invoke(container: RegistrationForEmailCodeContainer) =
        repository.registerForEmailCode(container)
}