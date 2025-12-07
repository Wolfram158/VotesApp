package ru.wolfram.vote.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class SecurityUtils {
    private val symbols = ('a'..'z').toSet() + ('A'..'Z').toSet() + ('0'..'9').toSet()

    fun randomString(length: Int): String {
        return buildString {
            repeat(length) {
                append(symbols.random())
            }
        }
    }

    @Bean
    fun bcryptEncoder(
        @Value($$"${bcrypt.strength}") strength: Int
    ): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(strength)
    }
}