package ru.wolfram.votes.di

import dagger.Component
import ru.wolfram.common.di.AppComponent
import ru.wolfram.votes.presentation.VotesViewModelFactory

@VotesScope
@Component(
    dependencies = [AppComponent::class],
    modules = [VotesRepositoryModule::class]
)
interface VotesComponent {
    @VotesScope
    fun getVotesViewModelFactory(): VotesViewModelFactory
}