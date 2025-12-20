package ru.wolfram.vote.presentation

import kotlinx.serialization.Serializable

@Serializable
data class RefreshWithEmailCode(
    val username: String
)