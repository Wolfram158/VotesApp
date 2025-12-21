package ru.wolfram.vote.presentation

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationWithEmailCode(
    val username: String,
    val email: String,
    val password: String
)