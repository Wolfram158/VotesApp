package ru.wolfram.vote.dto

import io.jsonwebtoken.Claims

sealed interface TokenValidationResult {
    data object BadToken : TokenValidationResult

    data object BadTime : TokenValidationResult

    data class Success(val claims: Claims) : TokenValidationResult
}