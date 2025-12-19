package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import ru.wolfram.vote.data.create_vote.repository.CreateVoteRepositoryImpl
import ru.wolfram.vote.domain.create_vote.repository.CreateVoteRepository

@Module
interface CreateVoteModule {
    @CreateVoteScope
    @Binds
    fun bindCreateVoteRepository(impl: CreateVoteRepositoryImpl): CreateVoteRepository
}