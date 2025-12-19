package ru.wolfram.vote.domain.registration_for_email_code.repository

import ru.wolfram.vote.domain.registration_for_email_code.model.RegistrationForEmailCodeContainer

interface RegistrationForEmailCodeRepository {
    suspend fun registerForEmailCode(container: RegistrationForEmailCodeContainer)
}