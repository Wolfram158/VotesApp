package ru.wolfram.create_vote.presentation

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class CreateVoteVariant(
    val variant: String,
    val id: String = Uuid.random().toString()
)
