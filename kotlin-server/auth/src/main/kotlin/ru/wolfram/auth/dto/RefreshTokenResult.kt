package ru.wolfram.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

sealed interface RefreshTokenResult {
    data object RefreshTokenNotFound : RefreshTokenResult

    data object IncorrectRefreshToken : RefreshTokenResult

    @Schema(description = "Result of generating new pair of tokens")
    data class Success(
        @field:Schema(description = "Access token")
        val token: String,
        @field:Schema(description = "Refresh token")
        val refreshToken: String
    ) : RefreshTokenResult
}