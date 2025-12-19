package ru.wolfram.vote.domain.votes.model

data class Vote(
    val title: String,
    val variant: String,
    val votesCount: Long
)