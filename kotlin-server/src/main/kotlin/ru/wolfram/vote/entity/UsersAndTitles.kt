package ru.wolfram.vote.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users_and_titles")
data class UsersAndTitles(
    @EmbeddedId
    var usersAndTitlesPrimaryKey: UsersAndTitlesPrimaryKey? = null,
)