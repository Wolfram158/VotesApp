package ru.wolfram.common.domain.model

data class Vote(
    val title: String,
    val variant: String,
    val votesCount: Long
)