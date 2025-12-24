package ru.wolfram.votes.di

import dagger.Binds
import dagger.Module
import ru.wolfram.votes.data.repository.VotesRepositoryImpl
import ru.wolfram.votes.domain.repository.VotesRepository

@Module
internal interface VotesRepositoryModule {
    @VotesScope
    @Binds
    fun bindVotesRepository(impl: VotesRepositoryImpl): VotesRepository
}