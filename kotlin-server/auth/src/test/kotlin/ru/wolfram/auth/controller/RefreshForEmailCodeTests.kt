package ru.wolfram.auth.controller

import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.wolfram.auth.dto.UserDto2
import kotlin.test.Test

class RefreshForEmailCodeTests : BaseEndpointTest() {
    private fun refreshForEmailCodeBase(
        username: String,
        maybeBadUsername: String,
        email: String,
        password: String,
        maybeBadPassword: String,
        code: String,
        matcher: ResultMatcher,
        additionalAction: () -> Unit = {}
    ) {
        val encoded = encoder.encode(code)
        registerWithEmailCode(
            username = username,
            email = email,
            password = password,
            code = code,
            ResponseEntity.ok(encoded),
            MockMvcResultMatchers.status().isOk
        )
        additionalAction()
        val request = MockMvcRequestBuilders.post("$BASE_PREFIX/refresh-for-email-code")
            .content(
                objectMapper.writeValueAsString(
                    UserDto2(
                        username = maybeBadUsername,
                        password = maybeBadPassword
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON)
        mockMvc
            .perform(request)
            .andExpect(matcher)
    }

    @Test
    fun `WHEN user not exists THEN 400 code returned`() {
        val username = "Paul"
        val email = "hello-world@yaml.com"
        val password = "5678"
        val code = "1234"
        refreshForEmailCodeBase(
            username = username,
            maybeBadUsername = "${username}a",
            email = email,
            password = password,
            maybeBadPassword = password,
            code = code,
            matcher = MockMvcResultMatchers.status().isBadRequest
        )
    }

    @Test
    fun `WHEN password does not match expected value THEN 400 code returned`() {
        val username = "Paul"
        val email = "hello-world@yaml.com"
        val password = "5678"
        val code = "1234"
        refreshForEmailCodeBase(
            username = username,
            maybeBadUsername = username,
            email = email,
            password = password,
            maybeBadPassword = "${password}a",
            code = code,
            matcher = MockMvcResultMatchers.status().isBadRequest
        )
    }

    @Test
    fun `WHEN ok THEN ok`() {
        val username = "Paul"
        val email = "hello-world@yaml.com"
        val password = "5678"
        val code = "1234"
        refreshForEmailCodeBase(
            username = username,
            maybeBadUsername = username,
            email = email,
            password = password,
            maybeBadPassword = password,
            code = code,
            matcher = MockMvcResultMatchers.status().isOk
        ) {
            `when`(mailService.sendEmailCode(username, email))
                .thenReturn(ResponseEntity.ok().build())
        }
    }

    @Test
    fun `WHEN mail service does not perform query successfully THEN 500 code is returned`() {
        val username = "Paul"
        val email = "hello-world@yaml.com"
        val password = "5678"
        val code = "1234"
        refreshForEmailCodeBase(
            username = username,
            maybeBadUsername = username,
            email = email,
            password = password,
            maybeBadPassword = password,
            code = code,
            matcher = MockMvcResultMatchers.status().isInternalServerError
        ) {
            `when`(mailService.sendEmailCode(username, email))
                .thenReturn(ResponseEntity.status(HttpStatusCode.valueOf(500)).build())
        }
    }
}