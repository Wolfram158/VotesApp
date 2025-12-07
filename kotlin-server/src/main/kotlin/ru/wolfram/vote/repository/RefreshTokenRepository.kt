package ru.wolfram.vote.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.wolfram.vote.entity.RefreshToken

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByUsername(username: String): RefreshToken?

    fun deleteByUsername(username: String)
}