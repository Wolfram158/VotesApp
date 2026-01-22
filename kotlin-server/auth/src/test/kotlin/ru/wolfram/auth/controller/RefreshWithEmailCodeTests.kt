package ru.wolfram.auth.controller

import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.wolfram.auth.dto.RefreshWithEmailCodeState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RefreshWithEmailCodeTests : BaseEndpointTest() {
    @Test
    fun `WHEN mail service is unavailable THEN 500 code returned`() {
        val username = "Sir"
        val code = "1234"
        refreshWithEmailCode(
            username,
            code,
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(),
            MockMvcResultMatchers.status().isInternalServerError
        )
    }

    @Test
    fun `WHEN username not found in mail service THEN bad request returned`() {
        val username = "Sir21"
        val code = "1234"
        refreshWithEmailCode(
            username,
            code,
            ResponseEntity.status(HttpStatus.NOT_FOUND).build(),
            MockMvcResultMatchers.status().isBadRequest
        )
    }

    @Test
    fun `WHEN code is not the same as expected email code THEN bad request returned`() {
        val username = "Sir21"
        val code = "1234"
        val encoded = encoder.encode("${code}abc")
        refreshWithEmailCode(
            username,
            code,
            ResponseEntity.ok(encoded),
            MockMvcResultMatchers.status().isBadRequest
        )
    }

    @Test
    fun `WHEN ok THEN new refresh token is saved in table, refresh token in table = refresh token sent to user`() {
        val username = "Sir21"
        val email = "yum-install@kubectl.org"
        val password = "1234"
        val code = "5678"
        val encoded = encoder.encode(code)
        addUser(username, email, password)
        val givenRefreshToken = objectMapper.readValue(
            refreshWithEmailCode(
                username,
                code,
                ResponseEntity.ok(encoded),
                MockMvcResultMatchers.status().isOk
            ).andReturn().response.contentAsString,
            RefreshWithEmailCodeState.Success::class.java
        ).refreshToken
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