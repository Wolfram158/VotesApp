package ru.wolfram.vote.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.wolfram.vote.entity.UsersAndTitles

interface UsersAndTitlesRepository : JpaRepository<UsersAndTitles, Long> {
    @Query(
        "select * from users_and_titles u where u.username = :username and u.title = :title for update",
        nativeQuery = true
    )
    fun findByUsernameAndTitle(username: String, title: String): List<UsersAndTitles>
}