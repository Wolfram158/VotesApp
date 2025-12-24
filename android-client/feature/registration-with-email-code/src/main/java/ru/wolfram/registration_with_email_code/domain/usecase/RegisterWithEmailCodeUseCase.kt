package ru.wolfram.registration_with_email_code.domain.usecase

import ru.wolfram.registration_with_email_code.domain.model.RegistrationWithEmailCodeContainer
import ru.wolfram.registration_with_email_code.domain.repository.RegistrationWithEmailCodeRepository
import javax.inject.Inject

internal class RegisterWithEmailCodeUseCase @Inject constructor(
    private val repository: RegistrationWithEmailCodeRepository
) {
    suspend operator fun invoke(container: RegistrationWithEmailCodeContainer) =
        repository.registerWithEmailCode(container)
}