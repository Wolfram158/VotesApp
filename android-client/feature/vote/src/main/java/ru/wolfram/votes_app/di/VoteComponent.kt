package ru.wolfram.votes_app.di

import dagger.Component
import ru.wolfram.common.di.AppComponent
import ru.wolfram.votes_app.presentation.VoteViewModelFactory

@VoteScope
@Component(
    dependencies = [AppComponent::class],
    modules = [VoteRepositoryModule::class]
)
interface VoteComponent {
    @VoteScope
    fun getVoteViewModelFactory(): VoteViewModelFactory
}