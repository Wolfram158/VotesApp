package ru.wolfram.read_votes

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JacksonJsonSerializer
import ru.wolfram.read_votes.dto.VoteDto

@TestConfiguration
class KafkaConfiguration {
    @Value($$"${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Bean
    fun producerFactory(): ProducerFactory<String, List<VoteDto>> {
        val configProps = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JacksonJsonSerializer::class.java
        )
       val factory = DefaultKafkaProducerFactory<String, List<VoteDto>>(configProps)
        factory.valueSerializer = JacksonJsonSerializer<List<VoteDto>>()
        return factory
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, List<VoteDto>> {
        return KafkaTemplate(producerFactory())
    }
}