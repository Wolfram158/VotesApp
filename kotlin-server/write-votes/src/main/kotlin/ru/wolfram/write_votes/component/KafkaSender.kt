package ru.wolfram.write_votes.component

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import ru.wolfram.write_votes.dto.VoteDto

@Component
class KafkaSender(
    private val kafkaTemplate: KafkaTemplate<String, List<VoteDto>>
) {
    fun sendMessage(topicName: String, message: List<VoteDto>) {
        kafkaTemplate.send(topicName, message)
    }
}