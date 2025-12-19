package ru.wolfram.vote.data.security

import kotlinx.serialization.Serializable

@Serializable
data class EmailPreferences(
    val email: String? = null
)
