package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.wolfram.vote.data.repository.VoteRepositoryImpl
import ru.wolfram.vote.domain.repository.VoteRepository
import ru.wolfram.vote.presentation.VoteViewModel
import ru.wolfram.vote.presentation.VoteViewModelFactory
import javax.inject.Provider

@Module
internal interface VoteRepositoryModule {
    @VoteScope
    @Binds
    fun bindVoteRepository(impl: VoteRepositoryImpl): VoteRepository

    companion object {
        @VoteScope
        @Provides
        fun provideVoteViewModelFactory(provider: Provider<VoteViewModel>): VoteViewModelFactory {
            return VoteViewModelFactory(provider)
        }
    }
}