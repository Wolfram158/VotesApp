package ru.wolfram.common.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshWithEmailCodeContainerDto(
    @SerialName("username") val username: String,
    @SerialName("code") val code: String
)