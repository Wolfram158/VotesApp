package ru.wolfram.read_votes.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "votes")
data class Votes(
    @Id
    var id: Long? = null,
    var title: String? = null,
    var variant: String? = null,
    @Column("votes_count")
    var votesCount: Long? = null
)
