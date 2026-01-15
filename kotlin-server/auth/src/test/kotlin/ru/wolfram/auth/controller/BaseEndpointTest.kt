package ru.wolfram.auth.controller

import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ru.wolfram.auth.dto.RegistrationWithEmailCodeDto
import ru.wolfram.auth.entity.RefreshToken
import ru.wolfram.auth.entity.User
import ru.wolfram.auth.entity.UserPrimaryKey
import ru.wolfram.auth.service.MailService
import tools.jackson.module.kotlin.jacksonObjectMapper
import kotlin.test.BeforeTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
open class BaseEndpointTest {
    companion object {
        protected const val BASE_PREFIX = "/api/v1/auth"
        protected const val USERS_TABLE_NAME = "users"
        protected const val REFRESH_TOKENS_TABLE_NAME = "refresh_tokens"

        @JvmStatic
        protected val objectMapper = jacksonObjectMapper()

        @JvmStatic
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:18-alpine")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("POSTGRES_DB", postgres::getDatabaseName)
            registry.add("POSTGRES_PASSWORD", postgres::getPassword)
            registry.add("POSTGRES_USER", postgres::getUsername)

            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @MockitoBean
    protected lateinit var mailService: MailService

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    protected lateinit var encoder: PasswordEncoder

    @BeforeTest
    fun clear() {
        jdbcTemplate.execute("delete from $REFRESH_TOKENS_TABLE_NAME")
        jdbcTemplate.execute("delete from $USERS_TABLE_NAME")
    }

    protected fun getUsers(): List<User> {
        return jdbcTemplate.query("select * from $USERS_TABLE_NAME") { rs, _ ->
            User(
                userPrimaryKey = UserPrimaryKey(
                    rs.getString("username"),
                    rs.getString("email")
                ),
                password = rs.getString("password")
            )
        }
    }

    protected fun getRefreshTokens(): List<RefreshToken> {
        return jdbcTemplate.query("select * from $REFRESH_TOKENS_TABLE_NAME") { rs, _ ->
            RefreshToken(
                username = rs.getString("username"),
                refreshToken = rs.getString("refresh_token")
            )
        }
    }

    protected fun registerWithEmailCode(
        username: String,
        email: String,
        password: String,
        code: String,
        thenReturnValue: ResponseEntity<String>,
        matcher: ResultMatcher
    ): ResultActions {
        `when`(mailService.getEncodedEmailCodeByUsername(username))
            .thenReturn(thenReturnValue)
        val request = MockMvcRequestBuilders.post("$BASE_PREFIX/register-with-email-code")
            .content(
                objectMapper.writeValueAsString(
                    RegistrationWithEmailCodeDto(
                        username = username,
                        email = email,
                        password = password,
                        code = code
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON)
        return mockMvc
            .perform(request)
            .andExpect(matcher)
    }
}