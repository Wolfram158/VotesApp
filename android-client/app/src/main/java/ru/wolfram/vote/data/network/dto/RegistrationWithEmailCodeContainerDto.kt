package ru.wolfram.vote.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.wolfram.vote.domain.registration_with_email_code.model.RegistrationWithEmailCodeContainer

@Serializable
data class RegistrationWithEmailCodeContainerDto(
    @SerialName("username") val username: String,
    @SerialName("code") val code: String
)

fun RegistrationWithEmailCodeContainer.toRegistrationWithEmailCodeContainerDto() =
    RegistrationWithEmailCodeContainerDto(
        username = username,
        code = code
    )
