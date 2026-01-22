package ru.wolfram.auth.dto

import ru.wolfram.auth.validation.ValidEmail
import ru.wolfram.auth.validation.ValidUsername

data class RegistrationWithEmailCodeDto(
    @field:ValidUsername
    val username: String,
    @field:ValidEmail
    val email: String,
    val password: String,
    val code: String
)
