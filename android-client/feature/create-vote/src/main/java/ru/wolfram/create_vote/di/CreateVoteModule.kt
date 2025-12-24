package ru.wolfram.create_vote.di

import dagger.Binds
import dagger.Module
import ru.wolfram.create_vote.data.repository.CreateVoteRepositoryImpl
import ru.wolfram.create_vote.domain.repository.CreateVoteRepository

@Module
internal interface CreateVoteModule {
    @CreateVoteScope
    @Binds
    fun bindCreateVoteRepository(impl: CreateVoteRepositoryImpl): CreateVoteRepository
}