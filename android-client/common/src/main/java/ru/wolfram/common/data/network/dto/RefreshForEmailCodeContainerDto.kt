package ru.wolfram.common.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshForEmailCodeContainerDto(
    @SerialName("username") val username: String
)

