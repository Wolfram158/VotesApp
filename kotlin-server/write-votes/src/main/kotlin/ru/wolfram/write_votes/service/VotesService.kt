package ru.wolfram.write_votes.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.wolfram.write_votes.component.KafkaSender
import ru.wolfram.write_votes.dto.VoteDto
import ru.wolfram.write_votes.mapper.toDto
import ru.wolfram.write_votes.repository.VotesRepository

@Service
class VotesService(
    private val votesRepository: VotesRepository,
    private val kafkaSender: KafkaSender
) {
    @Transactional
    suspend fun create(vote: List<VoteDto>): Long {
        require(vote.map { it.title }.distinct().size == 1)
        require(vote.map { it.variant }.distinct().size == vote.size)
        require(votesRepository.findByTitle(vote[0].title).isEmpty())
        var count = 0L
        vote.forEach {
            count += votesRepository.insertTitleAndVariant(title = it.title, variant = it.variant)
        }
        return count
    }

    @Transactional
    suspend fun getRandomVote(): List<VoteDto> {
        return votesRepository.getRandomVote().map { it.toDto() }
    }

    @Transactional
    suspend fun acceptVote(title: String) {
        val vote = votesRepository.findByTitle(title).map { it.toDto() }
        require(vote.isNotEmpty())
        kafkaSender.sendMessage("votes-1", vote)
        votesRepository.deleteByTitle(title)
    }

    @Transactional
    suspend fun rejectVote(title: String) {
        votesRepository.deleteByTitle(title)
    }
}