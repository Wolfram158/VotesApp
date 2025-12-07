package ru.wolfram.vote.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.wolfram.vote.dto.VoteDto
import ru.wolfram.vote.dto.VoteDto2
import ru.wolfram.vote.entity.UsersAndTitles
import ru.wolfram.vote.entity.UsersAndTitlesPrimaryKey
import ru.wolfram.vote.entity.Votes
import ru.wolfram.vote.repository.UsersAndTitlesRepository
import ru.wolfram.vote.repository.VotesRepository

@Service
class VotesService(
    private val votesRepository: VotesRepository,
    private val usersAndTitlesRepository: UsersAndTitlesRepository
) {
    @Transactional
    fun saveVoteEntity(votes: List<VoteDto>) {
        require(votes.map { it.title }.distinct().size == 1)
        require(votes.map { it.variant }.distinct().size == votes.size)
        require(votesRepository.findByTitle(votes[0].title) != null)
        votes.forEach {
            votesRepository.save(Votes(title = it.title, variant = it.variant, votesCount = 0))
        }
    }

    @Transactional
    fun incrementVotesCount(username: String, title: String, variant: String) {
        require(usersAndTitlesRepository.findByUsernameAndTitle(username, title).isEmpty())
        votesRepository.incrementVotesCount(title, variant)
        usersAndTitlesRepository.save(UsersAndTitles(UsersAndTitlesPrimaryKey(username, title)))
    }

    @Transactional
    fun findAll(): Map<String, List<VoteDto2>> {
        return votesRepository.findAll()
            .map {
                VoteDto2(
                    it.title!!,
                    it.variant!!,
                    it.votesCount!!
                )
            }
            .groupBy { it.title }
    }
}