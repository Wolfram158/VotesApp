package ru.wolfram.votes_app.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.wolfram.votes_app.data.repository.VoteRepositoryImpl
import ru.wolfram.votes_app.domain.repository.VoteRepository
import ru.wolfram.votes_app.presentation.VoteViewModel
import ru.wolfram.votes_app.presentation.VoteViewModelFactory
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