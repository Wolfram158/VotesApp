package ru.wolfram.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import ru.wolfram.email.entity.Session
import ru.wolfram.email.repository.SessionRepository
import kotlin.jvm.optionals.getOrNull
import kotlin.test.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
class EmailApplicationTests {
    companion object {
        private const val BASE_PREFIX = "/api/v1/email"

        @JvmStatic
        @Container
        val redis: GenericContainer<*> = GenericContainer(
            DockerImageName.parse("redis:8.4-alpine")
        ).withExposedPorts(6379)

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.redis.host") { redis.host }
            registry.add("spring.data.redis.port") { redis.getMappedPort(6379).toString() }
        }
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var sessionRepository: SessionRepository

    @Autowired
    private lateinit var encoder: PasswordEncoder

    @AfterTest
    fun clean() {
        sessionRepository.deleteAll()
    }

    @Test
    fun `GIVEN session insertion into redis WHEN timeout THEN session is destroyed`() {
        val username = "Paul"
        sessionRepository.save(Session(username, "1234", 3))
        Thread.sleep(4000)
        assertNull(sessionRepository.findById(username).getOrNull())
    }

    @Test
    fun `GIVEN session insertion into redis WHEN no timeout THEN session exists`() {
        val username = "Paul"
        sessionRepository.save(Session(username, "1234", 3))
        Thread.sleep(1500)
        assertNotNull(sessionRepository.findById(username).getOrNull())
    }

    @Test
    fun `GIVEN session WHEN get session through REST and no timeout THEN success`() {
        val username = "Paul"
        val code = "1234"
        val encoded = encoder.encode(code)
        assertNotNull(encoded)
        sessionRepository.save(
            Session(username, encoded, 5)
        )
        val request = MockMvcRequestBuilders.get("$BASE_PREFIX/get-encoded-email-code-by-username")
            .queryParam("username", username)
        val givenEncoded = mockMvc
            .perform(request)
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
        assertEquals(encoded, givenEncoded)
    }

    @Test
    fun `GIVEN session WHEN get session through REST and non-expected name THEN not found returned`() {
        val username = "Paul"
        val otherUsername = "Andrew"
        assertNotEquals(username, otherUsername)
        val code = "1234"
        val encoded = encoder.encode(code)
        assertNotNull(encoded)
        sessionRepository.save(
            Session(username, encoded, 5)
        )
        val request = MockMvcRequestBuilders.get("$BASE_PREFIX/get-encoded-email-code-by-username")
            .queryParam("username", otherUsername)
        mockMvc
            .perform(request)
            .andExpect(status().isNotFound)
    }

}
