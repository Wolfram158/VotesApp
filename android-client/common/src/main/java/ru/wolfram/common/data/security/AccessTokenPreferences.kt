package ru.wolfram.common.data.security

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenPreferences(
    val token: String? = null
)