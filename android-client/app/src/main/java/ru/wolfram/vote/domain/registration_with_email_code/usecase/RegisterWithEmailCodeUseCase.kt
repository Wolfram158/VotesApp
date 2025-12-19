package ru.wolfram.vote.domain.registration_with_email_code.usecase

import ru.wolfram.vote.domain.registration_with_email_code.model.RegistrationWithEmailCodeContainer
import ru.wolfram.vote.domain.registration_with_email_code.repository.RegistrationWithEmailCodeRepository
import javax.inject.Inject

class RegisterWithEmailCodeUseCase @Inject constructor(
    private val repository: RegistrationWithEmailCodeRepository
) {
    suspend operator fun invoke(container: RegistrationWithEmailCodeContainer) =
        repository.registerWithEmailCode(container)
}