package ru.wolfram.email.component

import org.springframework.stereotype.Component

@Component
class SecurityUtils {
    private val symbols = ('a'..'z').toSet() + ('A'..'Z').toSet() + ('0'..'9').toSet()

    fun randomString(length: Int): String {
        return (1..length)
            .map { symbols.random() }
            .joinToString("")
    }
}