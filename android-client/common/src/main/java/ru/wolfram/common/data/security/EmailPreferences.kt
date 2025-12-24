package ru.wolfram.common.data.security

import kotlinx.serialization.Serializable

@Serializable
data class EmailPreferences(
    val email: String? = null
)
