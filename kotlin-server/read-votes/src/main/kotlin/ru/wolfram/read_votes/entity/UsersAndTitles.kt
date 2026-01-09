package ru.wolfram.read_votes.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table(name = "users_and_titles")
data class UsersAndTitles(
    @Id
    var username: String? = null,
    @Id
    var title: String? = null
)