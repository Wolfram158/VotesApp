package ru.wolfram.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.wolfram.auth.entity.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserPrimaryKeyUsername(username: String): User?

    fun findEmailByUserPrimaryKeyUsername(username: String): User?
}