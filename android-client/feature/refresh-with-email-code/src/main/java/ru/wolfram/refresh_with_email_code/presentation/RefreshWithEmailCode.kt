package ru.wolfram.refresh_with_email_code.presentation

import kotlinx.serialization.Serializable

@Serializable
data class RefreshWithEmailCode(
    val username: String
)