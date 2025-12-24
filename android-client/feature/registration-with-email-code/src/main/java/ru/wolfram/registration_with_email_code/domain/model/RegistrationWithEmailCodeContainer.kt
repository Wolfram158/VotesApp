package ru.wolfram.registration_with_email_code.domain.model

data class RegistrationWithEmailCodeContainer(
    val username: String,
    val email: String,
    val password: String,
    val code: String
)
