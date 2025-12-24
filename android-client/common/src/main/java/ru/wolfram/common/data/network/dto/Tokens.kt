package ru.wolfram.common.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
    @SerialName("token") val token: String,
    @SerialName("refreshToken") val refreshToken: String
)