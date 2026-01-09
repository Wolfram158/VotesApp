package ru.wolfram.administration.service

import ru.wolfram.administration.model.Vote

interface AdministrationService {
    fun getVoteOffer(): List<Vote>?

    fun accept(title: String)

    fun reject(title: String)
}