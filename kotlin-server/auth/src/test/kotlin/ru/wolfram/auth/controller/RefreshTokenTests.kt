package ru.wolfram.auth.controller

import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.wolfram.auth.constants.Constants
import ru.wolfram.auth.dto.RegistrationWithEmailCodeState
import ru.wolfram.auth.security.JwtGenerator
import kotlin.test.Test

@TestPropertySource(properties = ["jwt.accessTokenExpiration=7000"])
class RefreshTokenTests : BaseEndpointTest() {
    @Autowired
    private lateinit var jwtGenerator: JwtGenerator

    @Value($$"${jwt.accessTokenExpiration}")
    private lateinit var accessTokenExpiration: java.lang.Long

    private fun refreshTokenBase(
        username: String,
        email: String,
        password: String,
        code: String,
        waitMillis: Long,
        matcher: ResultMatcher,
        refreshToken: String? = null
    ) {
        val encoded = encoder.encode(code)
        `when`(mailService.getEncodedEmailCodeByUsername(username))
            .thenReturn(ResponseEntity.ok(encoded))
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
        Thread.sleep(waitMillis)
        val request = MockMvcRequestBuilders.post("$BASE_PREFIX/refresh-token")
            .header(Constants.AUTHORIZATION_HEADER_KEY, refreshToken ?: givenRefreshToken)
            .contentType(MediaType.APPLICATION_JSON)
        mockMvc
            .perform(request)
            .andExpect(matcher)
    }

    @Test
    fun `WHEN ok THEN ok`() {
        val username = "Philipp"
        val email = "phillip256@yandex.com"
        val password = "Hello Clojure"
        val code = "1234"
        refreshTokenBase(
            username,
            email,
            password,
            code,
            accessTokenExpiration.toLong() + 1000,
            MockMvcResultMatchers.status().isOk
        )
    }

    @Test
    fun `WHEN access token is active yet THEN new refresh token must not be generated and 400 code returned`() {
        val username = "Dijkstra"
        val email = "phillip512@yandex.com"
        val password = "Hello Haskell"
        val code = "12345"
        refreshTokenBase(
            username,
            email,
            password,
            code,
            (accessTokenExpiration.toDouble() / 7).toLong(),
            MockMvcResultMatchers.status().isBadRequest
        )
    }

    @Test
    fun `WHEN refresh token is not the same as expected THEN 400 code returned`() {
        val username = "Dijkstra"
        val email = "phillip512@yandex.com"
        val password = "Hello Haskell"
        val code = "12345"
        val badRefreshToken = jwtGenerator.generateToken(
            username,
            1_000_000_000,
            0,
            System.currentTimeMillis()
        )
        refreshTokenBase(
            username,
            email,
            password,
            code,
            accessTokenExpiration.toLong() + 1000,
            MockMvcResultMatchers.status().isBadRequest,
            badRefreshToken
        )
    }
}