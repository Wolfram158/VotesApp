package ru.wolfram.vote.domain.security

interface Crypto {
    fun encrypt(bytes: ByteArray): ByteArray

    fun decrypt(bytes: ByteArray): ByteArray
}