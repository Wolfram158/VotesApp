package ru.wolfram.vote.dto

data class RefreshWithEmailCodeDto(
    val username: String,
    val code: String
)
