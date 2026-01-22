package ru.wolfram.auth.dto

import ru.wolfram.auth.validation.ValidEmail
import ru.wolfram.auth.validation.ValidUsername

data class UserDto(
    @field:ValidUsername
    val username: String,
    @field:ValidEmail
    val email: String
)
