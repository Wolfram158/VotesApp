package ru.wolfram.vote.di

import dagger.Component
import ru.wolfram.vote.presentation.VotesViewModelFactory

@VotesScope
@Component(
    dependencies = [AppComponent::class],
    modules = [VotesRepositoryModule::class]
)
interface VotesComponent {
    @VotesScope
    fun getVotesViewModelFactory(): VotesViewModelFactory
}