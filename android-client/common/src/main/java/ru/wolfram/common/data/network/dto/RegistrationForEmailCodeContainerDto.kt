package ru.wolfram.common.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.wolfram.vote.domain.registration_for_email_code.model.RegistrationForEmailCodeContainer

@Serializable
data class RegistrationForEmailCodeContainerDto(
    @SerialName("username") val username: String,
    @SerialName("password") val password: String,
    @SerialName("email") val email: String
)

fun RegistrationForEmailCodeContainer.toRegistrationForEmailCodeContainerDto() =
    RegistrationForEmailCodeContainerDto(
        username = username,
        password = password,
        email = email
    )