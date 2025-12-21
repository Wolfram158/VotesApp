package ru.wolfram.vote.domain.registration_with_email_code.model

data class RegistrationWithEmailCodeContainer(
    val username: String,
    val email: String,
    val password: String,
    val code: String
)
