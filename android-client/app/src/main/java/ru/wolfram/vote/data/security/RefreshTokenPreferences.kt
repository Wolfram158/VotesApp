package ru.wolfram.vote.data.security

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenPreferences(
    val token: String? = null
)
