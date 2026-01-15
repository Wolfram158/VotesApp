package ru.wolfram.auth.controller

import org.mockito.Mockito
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.wolfram.auth.dto.UserDto
import kotlin.test.Test

class RegisterForEmailCodeTests : BaseEndpointTest() {
    private fun base(
        username: String,
        email: String,
        thenReturnValue: ResponseEntity<String>,
        matcher: ResultMatcher
    ) {
        Mockito.`when`(
            mailService
                .sendEmailCode(username = username, email = email)
        ).thenReturn(thenReturnValue)
        val request = MockMvcRequestBuilders.post("$BASE_PREFIX/register-for-email-code")
            .content(
                objectMapper.writeValueAsString(
                    UserDto(
                        username = username,
                        email = email
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON)
        mockMvc
            .perform(request)
            .andExpect(matcher)
    }

    @Test
    fun `WHEN email service is unavailable THEN 500 code is returned`() {
        val username = "Andrew"
        val email = "andrew227@tanki.su"
        base(
            username,
            email,
            ResponseEntity.internalServerError().build(),
            MockMvcResultMatchers.status().isInternalServerError
        )
    }

    @Test
    fun `WHEN user already exists THEN 400 code is returned`() {
        val username = "Andrew"
        val email = "andrew227@tanki.su"
        val password = "1234"
        jdbcTemplate.execute("insert into users (username, email, password) values ('$username', '$email', '$password')")
        base(
            username,
            email,
            ResponseEntity.ok().build(),
            MockMvcResultMatchers.status().isBadRequest
        )
    }

    @Test
    fun `WHEN no problem THEN 200 code is returned`() {
        val username = "Andrew"
        val email = "andrew227@tanki.su"
        base(
            username,
            email,
            ResponseEntity.ok().build(),
            MockMvcResultMatchers.status().isOk
        )
    }

    @Test
    fun `WHEN username contains whitespace THEN 400 code is returned`() {
        val username = "Carl Johnson"
        val email = "carl-johnson229@tanki.us"
        base(
            username,
            email,
            ResponseEntity.ok().build(),
            MockMvcResultMatchers.status().isBadRequest
        )
    }

}