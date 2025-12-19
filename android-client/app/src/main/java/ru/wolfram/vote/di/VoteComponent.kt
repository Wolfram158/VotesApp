package ru.wolfram.vote.di

import dagger.Component

@VoteScope
@Component(
    dependencies = [AppComponent::class],
    modules = [VoteRepositoryModule::class]
)
interface VoteComponent {
}