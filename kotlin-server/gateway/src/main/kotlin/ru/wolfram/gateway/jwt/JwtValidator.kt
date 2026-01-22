package ru.wolfram.gateway.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Base64
import javax.crypto.spec.SecretKeySpec

@Component
class JwtValidator(
    @param:Value($$"${jwt.secret}") private val secret: String,
) {
    private val signingKey: SecretKeySpec
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
        }

    fun extractAllClaims(token: String?): Claims {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun isJwtValid(token: String?): Boolean {
        return try {
            extractAllClaims(token)
            true
        } catch (_: Exception) {
            false
        }
    }
}