package ru.wolfram.read_votes.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.wolfram.read_votes.entity.UsersAndTitles

@Repository
interface UsersAndTitlesRepository : CoroutineCrudRepository<UsersAndTitles, Long> {
    @Query(
        "select * from users_and_titles u where u.username = :username and u.title = :title for update"
    )
    suspend fun findByUsernameAndTitle(
        @Param("username") username: String,
        @Param("title") title: String
    ): List<UsersAndTitles>
}