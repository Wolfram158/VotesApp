package ru.wolfram.common.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationForEmailCodeContainerDto(
    @SerialName("username") val username: String,
    @SerialName("email") val email: String
)