package ru.wolfram.vote.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.wolfram.vote.domain.refresh_with_email_code.model.RefreshWithEmailCodeContainer

@Serializable
data class RefreshWithEmailCodeContainerDto(
    @SerialName("username") val username: String,
    @SerialName("code") val code: String
)

fun RefreshWithEmailCodeContainer.toRefreshWithEmailCodeContainerDto() =
    RefreshWithEmailCodeContainerDto(
        username = username,
        code = code
    )