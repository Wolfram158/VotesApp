package ru.wolfram.vote.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteDto(
    @SerialName("title") val title: String,
    @SerialName("variant") val variant: String
)