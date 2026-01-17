package ru.wolfram.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Information necessary for accepting token refresh")
data class RefreshWithEmailCodeDto(
    @field:Schema(description = "Username")
    val username: String,
    @field:Schema(description = "Code from email message")
    val code: String
)
