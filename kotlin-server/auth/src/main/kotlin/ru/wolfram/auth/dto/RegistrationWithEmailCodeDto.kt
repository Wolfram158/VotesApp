package ru.wolfram.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import ru.wolfram.auth.validation.ValidEmail
import ru.wolfram.auth.validation.ValidUsername

@Schema(description = "Information necessary for ending of registration")
data class RegistrationWithEmailCodeDto(
    @field:Schema(description = "Username")
    @field:ValidUsername
    val username: String,
    @field:Schema(description = "Email")
    @field:ValidEmail
    val email: String,
    @field:Schema(description = "Password")
    val password: String,
    @field:Schema(description = "Code")
    val code: String
)
