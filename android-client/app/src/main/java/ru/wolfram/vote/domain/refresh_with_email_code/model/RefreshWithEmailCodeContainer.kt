package ru.wolfram.vote.domain.refresh_with_email_code.model

data class RefreshWithEmailCodeContainer(
    val username: String,
    val code: String
)