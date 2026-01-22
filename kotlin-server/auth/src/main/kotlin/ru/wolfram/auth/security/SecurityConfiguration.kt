package ru.wolfram.auth.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

@Configuration
class SecurityConfiguration {
    @Bean
    fun pbkdf2PasswordEncoder(
        @Value($$"${pbkdf2.secret}") secret: String,
        @Value($$"${pbkdf2.saltLength}") saltLength: Int,
        @Value($$"${pbkdf2.iterations}") iterations: Int
    ): PasswordEncoder {
        return Pbkdf2PasswordEncoder(
            secret,
            saltLength,
            iterations,
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        )
    }
}