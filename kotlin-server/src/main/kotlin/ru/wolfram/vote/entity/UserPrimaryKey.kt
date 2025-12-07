package ru.wolfram.vote.entity

import jakarta.persistence.Embeddable

@Embeddable
data class UserPrimaryKey(
    var username: String? = null,
    var email: String? = null
)
