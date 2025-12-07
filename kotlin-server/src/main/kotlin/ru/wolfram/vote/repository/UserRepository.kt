package ru.wolfram.vote.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.wolfram.vote.entity.User

interface UserRepository : JpaRepository<User, Long> {
    @Query
    fun findByUserPrimaryKeyUsername(username: String): User?

    @Query
    fun findEmailByUserPrimaryKeyUsername(username: String): User?
}