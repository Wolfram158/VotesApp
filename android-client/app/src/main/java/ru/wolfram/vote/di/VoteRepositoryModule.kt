package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import ru.wolfram.vote.data.vote.repository.VoteRepositoryImpl
import ru.wolfram.vote.domain.vote.repository.VoteRepository

@Module
interface VoteRepositoryModule {
    @VoteScope
    @Binds
    fun bindVoteRepository(impl: VoteRepositoryImpl): VoteRepository
}