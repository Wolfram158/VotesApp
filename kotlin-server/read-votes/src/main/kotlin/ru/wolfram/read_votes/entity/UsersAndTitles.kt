package ru.wolfram.read_votes.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "users_and_titles")
data class UsersAndTitles(
    var username: String? = null,
    var title: String? = null,
    @Id
    var id: Long? = null
)