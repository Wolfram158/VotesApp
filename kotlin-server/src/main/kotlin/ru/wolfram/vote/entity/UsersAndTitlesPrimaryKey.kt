package ru.wolfram.vote.entity

import jakarta.persistence.Embeddable

@Embeddable
data class UsersAndTitlesPrimaryKey(
    var username: String? = null,
    var title: String? = null
)
