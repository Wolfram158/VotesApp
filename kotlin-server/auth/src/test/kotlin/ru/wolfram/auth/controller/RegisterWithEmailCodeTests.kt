package ru.wolfram.auth.controller

import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.wolfram.auth.dto.RegistrationWithEmailCodeState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegisterWithEmailCodeTests : BaseEndpointTest() {
    @Test
    fun `WHEN mail service is unavailable THEN 500 code returned`() {
        val username = "Sir"
        val email = "sir-the-21@ya.hoo"
        val password = "1234"
        val code = "1234"
        registerWithEmailCode(
            username,
            email,
            password,
            code,
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(),
            MockMvcResultMatchers.status().isInternalServerError
        )
    }

    @Test
    fun `WHEN username not found in mail service THEN bad request returned`() {
        val username = "Sir21"
        val email = "sir-the-21st@ya.hoo"
        val password = "12345"
        val code = "5678"
        registerWithEmailCode(
            username,
            email,
            password,
            code,
            ResponseEntity.status(HttpStatus.NOT_FOUND).build(),
            MockMvcResultMatchers.status().isBadRequest
        )
    }

    @Test
    fun `WHEN code is not the same as expected email code THEN bad request returned`() {
        val username = "Sir21"
        val email = "sir-the-21st@ya.hoo"
        val password = "12345"
        val code = "5678"
        val encoded = encoder.encode("${code}abc")
        registerWithEmailCode(
            username,
            email,
            password,
            code,
            ResponseEntity.ok(encoded),
            MockMvcResultMatchers.status().isBadRequest
        )
    }

    @Test
    fun `WHEN ok THEN user and refresh token are saved in tables, refresh token in table = refresh token sent to user`() {
        val username = "Sir21"
        val email = "sir-the-21st@ya.hoo"
        val password = "12345"
        val code = "5678"
        val encoded = encoder.encode(code)
        val givenRefreshToken = objectMapper.readValue(
            registerWithEmailCode(
                username,
                email,
                password,
                code,
                ResponseEntity.ok(encoded),
                MockMvcResultMatchers.status().isOk
            ).andReturn().response.contentAsString,
            RegistrationWithEmailCodeState.Success::class.java
        ).refreshToken
        val users = getUsers()
        assertEquals(1, users.size)
        val user = users[0]
        assertAll(
            {
                assertEquals(email, user.userPrimaryKey?.email)
            },
            {
                assertEquals(username, user.userPrimaryKey?.username)
            },
            {
                assertTrue(encoder.matches(password, user.password))
            }
        )
        val refreshTokens = getRefreshTokens()
        assertEquals(1, refreshTokens.size)
        val refreshToken = refreshTokens[0]
        assertAll(
            {
                assertEquals(username, refreshToken.username)
            },
            {
                assertTrue(
                    encoder.matches(
                        givenRefreshToken, refreshToken.refreshToken
                    )
                )
            }
        )
    }
}