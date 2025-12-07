package ru.wolfram.vote.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import ru.wolfram.vote.entity.Session

@Configuration
class RedisConfiguration {
    @Bean
    fun getRedisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Session> {
        val redisTemplate = RedisTemplate<String, Session>()
        redisTemplate.connectionFactory = redisConnectionFactory
        return redisTemplate
    }
}