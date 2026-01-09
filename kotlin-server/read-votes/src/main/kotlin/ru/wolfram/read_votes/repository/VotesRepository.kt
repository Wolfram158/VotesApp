package ru.wolfram.read_votes.repository

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.wolfram.read_votes.entity.Votes

@Repository
interface VotesRepository : CoroutineCrudRepository<Votes, Long> {
    @Query("select title from votes limit :limit offset :offset")
    suspend fun findAllTitlesInRange(
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<String>

    suspend fun findByTitle(
        @Param("title") title: String
    ): List<Votes>

    @Modifying
    @Query(
        "update votes set votes_count = votes_count + 1 where title = :title and variant = :variant"
    )
    suspend fun incrementVotesCount(
        @Param("title") title: String,
        @Param("variant") variant: String
    )

}
