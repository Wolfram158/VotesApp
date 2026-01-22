package ru.wolfram.read_votes.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.wolfram.read_votes.dto.TitleDto
import ru.wolfram.read_votes.dto.VoteDto
import ru.wolfram.read_votes.dto.VoteDto2
import ru.wolfram.read_votes.entity.UsersAndTitles
import ru.wolfram.read_votes.mapper.toEntity
import ru.wolfram.read_votes.repository.UsersAndTitlesRepository
import ru.wolfram.read_votes.repository.VotesRepository

@Service
class VotesService(
    private val votesRepository: VotesRepository,
    private val usersAndTitlesRepository: UsersAndTitlesRepository
) {
    @Transactional
    suspend fun incrementVotesCount(username: String, title: String, variant: String): List<VoteDto2>? {
        require(usersAndTitlesRepository.findByUsernameAndTitle(username, title).isEmpty())
        votesRepository.incrementVotesCount(title, variant)
        usersAndTitlesRepository.save(UsersAndTitles(username = username, title = title))
        return votesRepository.findByTitle(title).map { VoteDto2(it.title!!, it.variant!!, it.votesCount!!) }
    }

    @Transactional
    suspend fun findAllInRange(limit: Int, offset: Int): List<String> {
        return votesRepository.findAllTitlesInRange(limit, offset)
    }

    @Transactional
    suspend fun findByTitle(titleDto: TitleDto): List<VoteDto2> {
        return votesRepository.findByTitle(titleDto.title).map {
            VoteDto2(
                it.title!!,
                it.variant!!,
                it.votesCount!!
            )
        }
    }

    @Transactional
    suspend fun saveVote(vote: List<VoteDto>) {
        if (vote.isEmpty() || votesRepository.findByTitle(vote.first().title).isNotEmpty()) {
            return
        }
        votesRepository.saveAll(vote.map { it.toEntity() }).collect {}
    }
}