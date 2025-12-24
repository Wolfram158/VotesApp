package ru.wolfram.registration_with_email_code.presentation

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationWithEmailCode(
    val username: String,
    val email: String,
    val password: String
)