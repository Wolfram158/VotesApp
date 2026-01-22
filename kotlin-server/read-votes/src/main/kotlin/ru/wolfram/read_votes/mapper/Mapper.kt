package ru.wolfram.read_votes.mapper

import ru.wolfram.read_votes.dto.VoteDto
import ru.wolfram.read_votes.entity.Votes

fun VoteDto.toEntity() = Votes(
    title = title,
    variant = variant,
    votesCount = 0
)