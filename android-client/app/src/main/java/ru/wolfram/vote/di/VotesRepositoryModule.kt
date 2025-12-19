package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import ru.wolfram.vote.data.votes.repository.VotesRepositoryImpl
import ru.wolfram.vote.domain.votes.repository.VotesRepository

@Module
interface VotesRepositoryModule {
    @VotesScope
    @Binds
    fun bindVotesRepository(impl: VotesRepositoryImpl): VotesRepository
}