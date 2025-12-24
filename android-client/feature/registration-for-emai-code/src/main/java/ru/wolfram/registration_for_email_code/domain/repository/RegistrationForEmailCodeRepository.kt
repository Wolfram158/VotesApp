package ru.wolfram.registration_for_email_code.domain.repository

import ru.wolfram.registration_for_email_code.domain.model.RegistrationForEmailCodeContainer

interface RegistrationForEmailCodeRepository {
    suspend fun registerForEmailCode(container: RegistrationForEmailCodeContainer): Result<Unit>
}