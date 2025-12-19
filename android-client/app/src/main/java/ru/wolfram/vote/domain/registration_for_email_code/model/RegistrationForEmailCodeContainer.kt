package ru.wolfram.vote.domain.registration_for_email_code.model

data class RegistrationForEmailCodeContainer(
    val username: String,
    val password: String,
    val email: String
)
