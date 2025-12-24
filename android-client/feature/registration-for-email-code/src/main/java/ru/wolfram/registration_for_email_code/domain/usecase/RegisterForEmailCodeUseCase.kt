package ru.wolfram.registration_for_email_code.domain.usecase

import ru.wolfram.registration_for_email_code.domain.model.RegistrationForEmailCodeContainer
import ru.wolfram.registration_for_email_code.domain.repository.RegistrationForEmailCodeRepository

import javax.inject.Inject

internal class RegisterForEmailCodeUseCase @Inject constructor(
    private val repository: RegistrationForEmailCodeRepository
) {
    suspend operator fun invoke(container: RegistrationForEmailCodeContainer) =
        repository.registerForEmailCode(container)
}