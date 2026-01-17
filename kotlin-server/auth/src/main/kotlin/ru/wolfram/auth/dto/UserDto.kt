package ru.wolfram.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import ru.wolfram.auth.validation.ValidEmail
import ru.wolfram.auth.validation.ValidUsername

@Schema(description = "Some information about user")
data class UserDto(
    @field:Schema(description = "Username")
    @field:ValidUsername
    val username: String,
    @field:Schema(description = "Email")
    @field:ValidEmail
    val email: String
)
