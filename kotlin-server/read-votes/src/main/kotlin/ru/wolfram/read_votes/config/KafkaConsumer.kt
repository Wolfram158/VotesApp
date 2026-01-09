package ru.wolfram.read_votes.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer
import org.springframework.kafka.support.serializer.JacksonJsonSerializer
import ru.wolfram.read_votes.dto.VoteDto
import tools.jackson.core.type.TypeReference

@Configuration
@EnableKafka
class KafkaConsumer {
    @Value($$"${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Bean
    fun consumerFactory(): ConsumerFactory<String, List<VoteDto>> {
        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JacksonJsonSerializer::class.java,
            JacksonJsonDeserializer.TRUSTED_PACKAGES to arrayOf(
                "ru.wolfram.write_votes.dto",
                "ru.wolfram.read_votes.dto"
            )
        )
        val deserializer = JacksonJsonDeserializer(
            object : TypeReference<List<VoteDto>>() {},
            false
        )
        deserializer.trustedPackages("ru.wolfram.write_votes.dto", "ru.wolfram.read_votes.dto")
        return DefaultKafkaConsumerFactory(props, StringDeserializer(), deserializer)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, List<VoteDto>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, List<VoteDto>>()
        factory.setConsumerFactory(consumerFactory())
        factory.setBatchListener(false)
        return factory
    }
}