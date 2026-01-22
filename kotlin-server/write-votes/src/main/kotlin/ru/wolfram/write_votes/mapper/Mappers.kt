package ru.wolfram.write_votes.mapper

import ru.wolfram.write_votes.dto.VoteDto
import ru.wolfram.write_votes.entity.Votes

fun Votes.toDto() = VoteDto(title!!, variant!!)