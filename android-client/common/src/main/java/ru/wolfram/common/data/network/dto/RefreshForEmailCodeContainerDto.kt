package ru.wolfram.common.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.wolfram.vote.domain.gateway.model.RefreshForEmailCodeContainer

@Serializable
data class RefreshForEmailCodeContainerDto(
    @SerialName("username") val username: String
)

fun RefreshForEmailCodeContainer.toRefreshForEmailCodeContainerDto() =
    RefreshForEmailCodeContainerDto(
        username = username
    )