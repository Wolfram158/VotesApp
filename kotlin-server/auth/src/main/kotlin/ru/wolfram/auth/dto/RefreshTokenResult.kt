package ru.wolfram.auth.dto

interface RefreshTokenResult {
    data object RefreshTokenNotFound : RefreshTokenResult

    data object IncorrectRefreshToken : RefreshTokenResult

    data class Success(
        val token: String,
        val refreshToken: String
    ) : RefreshTokenResult
}