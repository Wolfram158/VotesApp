package ru.wolfram.write_votes.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopic {
    @Bean
    fun topic(): NewTopic {
        return TopicBuilder.name("votes-1").build()
    }
}