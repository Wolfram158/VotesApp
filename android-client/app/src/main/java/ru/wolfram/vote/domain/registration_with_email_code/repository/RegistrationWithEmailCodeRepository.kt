package ru.wolfram.vote.domain.registration_with_email_code.repository

import ru.wolfram.vote.domain.registration_with_email_code.model.RegistrationWithEmailCodeContainer

interface RegistrationWithEmailCodeRepository {
    suspend fun registerWithEmailCode(container: RegistrationWithEmailCodeContainer)
}