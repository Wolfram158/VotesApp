package ru.wolfram.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.wolfram.auth.entity.RefreshToken

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByUsername(username: String): RefreshToken?

    fun deleteByUsername(username: String)
}