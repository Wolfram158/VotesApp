package ru.wolfram.auth.dto

data class RegistrationWithEmailCodeDto(
    val username: String,
    val email: String,
    val password: String,
    val code: String
)
