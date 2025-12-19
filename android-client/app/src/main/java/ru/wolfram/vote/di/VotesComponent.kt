package ru.wolfram.vote.di

import dagger.Component

@VotesScope
@Component(
    dependencies = [AppComponent::class],
    modules = [VotesRepositoryModule::class]
)
interface VotesComponent {
}