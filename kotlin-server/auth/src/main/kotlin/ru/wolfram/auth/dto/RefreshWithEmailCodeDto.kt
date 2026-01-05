package ru.wolfram.auth.dto

data class RefreshWithEmailCodeDto(
    val username: String,
    val code: String
)
