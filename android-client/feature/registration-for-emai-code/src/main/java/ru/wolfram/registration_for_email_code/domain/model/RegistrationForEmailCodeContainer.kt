package ru.wolfram.registration_for_email_code.domain.model

data class RegistrationForEmailCodeContainer(
    val username: String,
    val password: String,
    val email: String
)
