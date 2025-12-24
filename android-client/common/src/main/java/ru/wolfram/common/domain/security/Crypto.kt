package ru.wolfram.common.domain.security

interface Crypto {
    fun encrypt(bytes: ByteArray): ByteArray

    fun decrypt(bytes: ByteArray): ByteArray
}