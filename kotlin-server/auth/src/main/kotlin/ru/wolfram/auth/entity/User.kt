package ru.wolfram.auth.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @EmbeddedId
    var userPrimaryKey: UserPrimaryKey? = null,
    var password: String? = null
)