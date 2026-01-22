package ru.wolfram.write_votes.repository

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.wolfram.write_votes.entity.Votes

@Repository
interface VotesRepository : CoroutineCrudRepository<Votes, Long> {
    @Query("insert into votes (title, variant) values (:title, :variant) on conflict do nothing")
    @Modifying
    suspend fun insertTitleAndVariant(title: String, variant: String): Int

    @Query("select * from votes where title = :title")
    suspend fun findByTitle(title: String): List<Votes>

    @Query("select * from votes where title = (select title from votes order by random() limit 1)")
    suspend fun getRandomVote(): List<Votes>

    @Query("delete from votes where title = :title")
    suspend fun deleteByTitle(title: String)
}