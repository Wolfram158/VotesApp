package ru.wolfram.auth

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ru.wolfram.auth.service.MailService
import kotlin.test.BeforeTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
open class BaseEndpointTest {
    companion object {
        protected const val BASE_PREFIX = "/api/v1/auth"

        @JvmStatic
        protected val objectMapper = ObjectMapper()

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

    @BeforeTest
    fun clear() {
        jdbcTemplate.execute("delete from users")
        jdbcTemplate.execute("delete from refresh_tokens")
    }
}