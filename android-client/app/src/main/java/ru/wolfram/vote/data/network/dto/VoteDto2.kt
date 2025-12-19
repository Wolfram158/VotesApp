package ru.wolfram.vote.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.wolfram.vote.domain.votes.model.Vote

@Serializable
data class VoteDto2(
    @SerialName("title") val title: String,
    @SerialName("variant") val variant: String,
    @SerialName("votesCount") val votesCount: Long
)

fun VoteDto2.toVote() = Vote(
    title = title,
    variant = variant,
    votesCount = votesCount
)

fun List<VoteDto2>.toVotes() = map { it.toVote() }

fun Map<String, List<VoteDto2>>.toVoteMap() =
    mapValues { list -> list.value.map { unit -> unit.toVote() } }
