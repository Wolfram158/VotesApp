package ru.wolfram.vote.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.wolfram.vote.entity.Votes

interface VotesRepository : JpaRepository<Votes, Long> {
    fun findByTitle(title: String): List<Votes>?

    @Modifying
    @Query(
        "update votes set votes_count = votes_count + 1 where title = :title and variant = :variant",
        nativeQuery = true
    )
    fun incrementVotesCount(title: String, variant: String)

}
