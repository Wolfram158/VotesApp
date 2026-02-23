package ru.wolfram.common.presentation

import kotlinx.serialization.Serializable

@Serializable
object CreateVote

@Serializable
object Gateway

@Serializable
data class RefreshWithEmailCode(
    val username: String
)

@Serializable
object RegistrationForEmailCode

@Serializable
data class RegistrationWithEmailCode(
    val username: String,
    val email: String,
    val password: String
)

@Serializable
data class Vote(val title: String)

@Serializable
object Votes