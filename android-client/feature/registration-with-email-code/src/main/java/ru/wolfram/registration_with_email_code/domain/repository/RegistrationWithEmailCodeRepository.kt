package ru.wolfram.registration_with_email_code.domain.repository

import ru.wolfram.registration_with_email_code.domain.model.RegistrationWithEmailCodeContainer

interface RegistrationWithEmailCodeRepository {
    suspend fun registerWithEmailCode(container: RegistrationWithEmailCodeContainer): Result<Unit>
}