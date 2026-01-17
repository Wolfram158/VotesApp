package ru.wolfram.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Some information about user")
data class UserDto2(
    @field:Schema(description = "Username")
    val username: String,
    @field:Schema(description = "Password")
    val password: String
)