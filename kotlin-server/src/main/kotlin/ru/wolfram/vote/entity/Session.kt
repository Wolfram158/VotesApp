package ru.wolfram.vote.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(timeToLive = 600L)
data class Session(
    @Id
    val username: String,
    val code: String,
    @TimeToLive
    val expirationInSeconds: Long
)