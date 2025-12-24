package ru.wolfram.vote.di

import dagger.Component
import ru.wolfram.common.di.AppComponent
import ru.wolfram.vote.presentation.VoteViewModelFactory

@VoteScope
@Component(
    dependencies = [AppComponent::class],
    modules = [VoteRepositoryModule::class]
)
interface VoteComponent {
    @VoteScope
    fun getVoteViewModelFactory(): VoteViewModelFactory
}