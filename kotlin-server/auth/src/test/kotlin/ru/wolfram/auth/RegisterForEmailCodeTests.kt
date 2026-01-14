package ru.wolfram.auth

import org.mockito.Mockito.`when`
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.wolfram.auth.dto.UserDto
import kotlin.test.Test

class RegisterForEmailCodeTests : BaseEndpointTest() {
    @Test
    fun `WHEN email service is unavailable THEN 500 code is returned`() {
        val username = "Andrew"
        val email = "andrew227@tanki.su"
        val password = "1234"
        `when`(
            mailService
                .sendEmailCode(username = username, email = email)
        ).thenReturn(ResponseEntity.internalServerError().build())
        val request = MockMvcRequestBuilders.post("$BASE_PREFIX/register-for-email-code")
            .content(
                objectMapper.writeValueAsString(
                    UserDto(
                        username = username,
                        email = email,
                        password = password
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON)
        mockMvc
            .perform(request)
            .andExpect(status().isInternalServerError)
    }

    @Test
    fun `WHEN user already exists THEN 400 code is returned for register-for-email-code`() {
        val username = "Andrew"
        val email = "andrew227@tanki.su"
        val password = "1234"
        jdbcTemplate.execute("insert into users (username, email, password) values ('$username', '$email', '$password')")
        `when`(
            mailService
                .sendEmailCode(username = username, email = email)
        ).thenReturn(ResponseEntity.ok().build())
        val request = MockMvcRequestBuilders.post("$BASE_PREFIX/register-for-email-code")
            .content(
                objectMapper.writeValueAsString(
                    UserDto(
                        username = username,
                        email = email,
                        password = password
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON)
        mockMvc
            .perform(request)
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `WHEN no problem THEN 200 code is returned for register-for-email-code`() {
        val username = "Andrew"
        val email = "andrew227@tanki.su"
        val password = "1234"
        `when`(
            mailService
                .sendEmailCode(username = username, email = email)
        ).thenReturn(ResponseEntity.ok().build())
        val request = MockMvcRequestBuilders.post("$BASE_PREFIX/register-for-email-code")
            .content(
                objectMapper.writeValueAsString(
                    UserDto(
                        username = username,
                        email = email,
                        password = password
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON)
        mockMvc
            .perform(request)
            .andExpect(status().isOk)
    }

}
