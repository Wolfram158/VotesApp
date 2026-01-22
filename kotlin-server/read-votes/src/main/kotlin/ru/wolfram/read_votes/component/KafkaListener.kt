package ru.wolfram.read_votes.component

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.wolfram.read_votes.dto.VoteDto
import ru.wolfram.read_votes.service.VotesService

@Component
class KafkaListener {
    @Autowired
    private lateinit var service: VotesService

    @KafkaListener(
        topics = ["votes-1"],
        groupId = "votes",
        containerFactory = "kafkaListenerContainerFactory"
    )
    suspend fun onGetVote(vote: List<VoteDto>) {
        service.saveVote(vote)
    }
}