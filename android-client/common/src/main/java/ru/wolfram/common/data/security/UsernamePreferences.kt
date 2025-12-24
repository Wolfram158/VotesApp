package ru.wolfram.common.data.security

import kotlinx.serialization.Serializable

@Serializable
data class UsernamePreferences(
    val username: String? = null
)