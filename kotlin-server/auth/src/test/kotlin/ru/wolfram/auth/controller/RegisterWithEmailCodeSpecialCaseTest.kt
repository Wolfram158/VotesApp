package ru.wolfram.auth.controller

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.http.ResponseEntity
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.wolfram.auth.exception.RefreshTokenSavingException
import ru.wolfram.auth.repository.RefreshTokenRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class RegisterWithEmailCodeSpecialCaseTest : BaseEndpointTest() {
    @MockitoBean
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    @Test
    fun `WHEN exception is thrown during saving refresh token THEN rollback`() {
        val username = "Sir21"
        val email = "sir-the-21st@ya.hoo"
        val password = "12345"
        val code = "5678"
        val encoded = encoder.encode(code)
        `when`(
            refreshTokenRepository.save(
                any()
            )
        ).thenThrow(RefreshTokenSavingException())
        registerWithEmailCode(
            username,
            email,
            password,
            code,
            ResponseEntity.ok(encoded),
            MockMvcResultMatchers.status().isInternalServerError
        )
        assertEquals(0, getUsers().size)
        assertEquals(0, getRefreshTokens().size)
    }
}