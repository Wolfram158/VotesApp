package ru.wolfram.read_votes

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import ru.wolfram.read_votes.dto.TitlesDto
import ru.wolfram.read_votes.dto.VoteDto
import ru.wolfram.read_votes.dto.VoteDto2
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
@Testcontainers
class ReadVotesApplicationTests {
    @Autowired
    private lateinit var r2dbc: R2dbcEntityTemplate

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, List<VoteDto>>

    companion object {
        private const val BASE_PREFIX = "/api/v1/read-votes"

        @JvmStatic
        @Container
        @ServiceConnection
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:18-alpine")

        @JvmStatic
        @Container
        val kafka: KafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.7.7"))

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("POSTGRES_DB", postgres::getDatabaseName)
            registry.add("POSTGRES_PASSWORD", postgres::getPassword)
            registry.add("POSTGRES_USER", postgres::getUsername)

            registry.add(
                "spring.r2dbc.url"
            ) { "r2dbc:postgresql://${postgres.host}:${postgres.firstMappedPort}/${postgres.databaseName}" }
            registry.add("spring.r2dbc.username", postgres::getUsername)
            registry.add("spring.r2dbc.password", postgres::getPassword)
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)

            registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers)
            registry.add(
                "spring.kafka.producer.value-serializer",
                { "org.springframework.kafka.support.serializer.JacksonJsonSerializer" }
            )
            registry.add("spring.kafka.consumer.auto-offset-reset", { "earliest" })
        }
    }

    private fun DatabaseClient.insertIntoVotes(): DatabaseClient.GenericExecuteSpec {
        val title1 = "'Yes or no?'"
        val title2 = "'Do you agree?'"
        val title3 = "'Which framework do you prefer?'"
        return sql(
            "insert into votes (title, variant, votes_count) values " +
                    "($title1, 'Yes', 0)," +
                    "($title1, 'No', 0)," +
                    "($title2, 'Yes', 0)," +
                    "($title2, 'No', 0)," +
                    "($title2, 'I do not know', 0)," +
                    "($title3, 'Ktor', 0)," +
                    "($title3, 'Spring', 0)," +
                    "($title3, 'Quarkus', 0)," +
                    "($title3, 'Micronaut', 0)"
        )
    }

    private fun getVotesPage(page: Int = 1): TitlesDto? {
        return webTestClient
            .get()
            .uri("$BASE_PREFIX/votes?page=$page")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(TitlesDto::class.java)
            .returnResult()
            .responseBody
    }

    @AfterEach
    fun clear() = runTest {
        with(r2dbc.databaseClient) {
            sql("delete from votes").await()
            sql("delete from users_and_titles").await()
        }
    }

    @Test
    fun `WHEN want to get page = 1 of votes THEN page is got`() = runTest {
        r2dbc.databaseClient.insertIntoVotes().await()
        assertEquals(
            3,
            getVotesPage()
                ?.titles
                ?.size
        )
    }

    @Test
    fun `WHEN user does vote THEN table users_and_titles is changed`() = runTest {
        r2dbc.databaseClient.insertIntoVotes().await()
        val username = "TestUser"
        val variant = "Yes"
        val response = webTestClient
            .post()
            .uri("$BASE_PREFIX/do-vote?username=$username")
            .bodyValue(VoteDto(title = "Yes or no?", variant = variant))
            .exchange()
            .expectStatus()
            .isOk
            .expectBodyList(VoteDto2::class.java)
            .returnResult()
            .responseBody
        assertEquals(
            1,
            response
                ?.filter { it.variant == variant }
                ?.map { it.votesCount }
                ?.firstOrNull()
        )
        assertEquals(
            username,
            r2dbc.databaseClient.sql("select * from users_and_titles")
                .fetch()
                .one()
                .map { it["username"].toString() }
                .awaitSingle()
        )
    }

    @Test
    fun `WHEN user tries to do vote twice on the same vote THEN 500 http code is returned in the second attempt`() =
        runTest {
            r2dbc.databaseClient.insertIntoVotes().await()
            val username = "TestUser"
            webTestClient
                .post()
                .uri("$BASE_PREFIX/do-vote?username=$username")
                .bodyValue(VoteDto(title = "Yes or no?", variant = "Yes"))
                .exchange()
                .expectStatus()
                .isOk
            webTestClient
                .post()
                .uri("$BASE_PREFIX/do-vote?username=$username")
                .bodyValue(VoteDto(title = "Yes or no?", variant = "Yes"))
                .exchange()
                .expectStatus()
                .isEqualTo(500)
        }

    @Test
    fun `WHEN vote is sent through kafka THEN vote is saved`() {
        val title = "What is your name?"

        assertEquals(
            0,
            getVotesPage()
                ?.titles
                ?.size
        )

        Thread.sleep(3000)

        kafkaTemplate
            .send(
                "votes-1",
                listOf(
                    VoteDto(title = title, variant = "My name is..."),
                    VoteDto(title = title, variant = "My name is")
                )
            )
            .get()

        var isTrueReached = false
        for (i in 1..30) {
            Thread.sleep(1000L)
            val rowsCount = getVotesPage()
                ?.titles
                ?.size
            if (rowsCount == 1) {
                isTrueReached = true
                break
            }
        }

        if (!isTrueReached) {
            fail("Vote was not inserted into table")
        }
    }
}