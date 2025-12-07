package ru.wolfram.vote.repository

import org.springframework.data.repository.CrudRepository
import ru.wolfram.vote.entity.Session

interface SessionRepository : CrudRepository<Session, String> {
}