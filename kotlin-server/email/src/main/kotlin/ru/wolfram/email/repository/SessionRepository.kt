package ru.wolfram.email.repository

import org.springframework.data.repository.CrudRepository
import ru.wolfram.email.entity.Session

interface SessionRepository : CrudRepository<Session, String> {
}